package com.api.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.ReviewRequestDTO;
import com.api.ecommerce.dto.response.ProductReviewResponseDTO;
import com.api.ecommerce.dto.response.ReviewResponseDTO;
import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.OrderStatus;
import com.api.ecommerce.entity.Product;
import com.api.ecommerce.entity.Review;
import com.api.ecommerce.entity.User;
import com.api.ecommerce.repo.ReviewRepo;
import com.api.ecommerce.response.Response;
import com.api.ecommerce.service.ReviewService;
import org.springframework.util.CollectionUtils;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewRepo repo;
	
	@Autowired
	private CommonService service;
	
	public Response<?> retrieveAllReviews() {
		Response<List<ReviewResponseDTO>> response = new Response<>();
		
		List<Review> reviews = repo.findAll();
		
		if(CollectionUtils.isEmpty(reviews)) {
			response.setSuccess(false);
			response.setMessage("No Reviews Added!!");
			return response;
		}
		
		response.setSuccess(true);
		response.setMessage("Reviews Retrieved Successfully!!");
		response.setResponseData(reviews.stream().map(review -> service.convertReviewToReviewResponseDTO(review)).toList());
		
		return response;
	}
	
	public Response<?> retrieveReviewsGivenByUser(String userExternalId) {
		Response<List<ReviewResponseDTO>> response = new Response<>();
		
		User user = service.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_USER_FOUND);
			return response;
		}
		
		List<Review> reviews = repo.findByUser_EntityId(user.getEntityId());
		
		if(reviews == null || reviews.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No Reviews Added By User!!");
			return response;
		}
		
		response.setSuccess(true);
		response.setMessage("Reviews Retrieved Successfully Given By User!!");
		response.setResponseData(reviews.stream().map(review -> service.convertReviewToReviewResponseDTO(review)).toList());
		
		return response;
	}
	
	public Response<?> retrieveReviewsGivenToProduct(String productExternalId) {
		Response<ProductReviewResponseDTO> response = new Response<>();
		
		Product product = service.findProductByExternalId(productExternalId);
		
		if(product == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_PRODUCT_FOUND);
			return response;
		}
		
		List<Review> productReviews = repo.findByProduct_EntityId(product.getEntityId());
		
		if(productReviews == null || productReviews.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No Reviews Added to Product!!");
			return response;
		}
		
		response.setSuccess(true);
		response.setMessage("Reviews Retrieved Successfully Given To Product!!");
		response.setResponseData(service.convertReviewToProductReviewResponseDTO(productReviews, product));
		
		return response;
	}
	
	public Response<?> addReviewToProductByUser(ReviewRequestDTO reviewDto) {
		Response<ReviewResponseDTO> response = new Response<>();

		User user = service.findUserByExternalId(reviewDto.getUserExternalId());
		if (user == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_USER_FOUND);
			return response;
		}

		Product product = service.findProductByExternalId(reviewDto.getProductExternalId());
		if (product == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_PRODUCT_FOUND);
			return response;
		}
		if (!product.isActive()) {
			response.setSuccess(false);
			response.setMessage("Inactive Product.");
			return response;
		}

		boolean hasPurchasedProduct = user.getOrders() != null && user.getOrders().stream()
				.filter(order -> order.getOrderStatus() == OrderStatus.DELIVERED)
				.anyMatch(order -> order.getOrderItems() != null && order.getOrderItems().stream()
					.anyMatch(item -> item.getProduct() != null && item.getProduct().getEntityId().equals(product.getEntityId())));
		if (!hasPurchasedProduct) {
			response.setSuccess(false);
			response.setMessage("User must have purchased and received this product before reviewing it");
			return response;
		}

		if (repo.findByUser_EntityIdAndProduct_EntityId(user.getEntityId(), product.getEntityId()).isPresent()) {
			response.setSuccess(false);
			response.setMessage("User has already reviewed this product");
			return response;
		}
		
		Review review = service.convertReviewRequestDtoToReview(reviewDto);
		
		repo.save(review);
		
		response.setSuccess(true);
		response.setMessage("Reviews Added Successfully!!");
		response.setResponseData(service.convertReviewToReviewResponseDTO(review));
		
		return response;
	}
	
	public Response<?> updateReviewToProductByUser(String reviewExternalId, ReviewRequestDTO reviewDto) {
		Response<ReviewResponseDTO> response = new Response<>();
		
		Optional<Review> reviewFound = repo.findByExternalId(reviewExternalId);
		
		if(reviewFound.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("Review not found!!");
			return response;
		}
		
		Review review = reviewFound.get();
		
		if(service.isUpdated(review.getRating(), reviewDto.getRating())) {
			review.setRating(reviewDto.getRating());
		}
		
		if(service.isUpdated(review.getComment(), reviewDto.getComment())) {
			review.setComment(reviewDto.getComment());
		}
				
		repo.save(review);
		
		response.setSuccess(true);
		response.setMessage("Reviews Added Successfully!!");
		response.setResponseData(service.convertReviewToReviewResponseDTO(review));
		
		return response;
	}
	
	public Response<?> deleteReview(String reviewExternalId) {
		Response<ReviewResponseDTO> response = new Response<>();
		
		Optional<Review> review = repo.findByExternalId(reviewExternalId);
		
		if(review.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("Review not found!!");
			return response;
		}
		
		repo.deleteById(review.get().getEntityID());

		response.setSuccess(true);
		response.setMessage("Review deleted Successfully!!");
		
		return response;
	}

	@Override
	public Response<?> retrieveReviewByExternalId(String reviewExternalId) {
		Response<ReviewResponseDTO> response = new Response<>();
		
		Optional<Review> review = repo.findByExternalId(reviewExternalId);
		
		if(review.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("Review not found!!");
			return response;
		}
		
		response.setSuccess(true);
		response.setMessage("Review retrieved Successfully!!");
		response.setResponseData(service.convertReviewToReviewResponseDTO(review.get()));
		return response;
	}

}
