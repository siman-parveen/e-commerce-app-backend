package com.api.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.ReviewRequestDTO;
import com.api.ecommerce.dto.response.ProductReviewResponseDTO;
import com.api.ecommerce.dto.response.ReviewResponseDTO;
import com.api.ecommerce.entity.Product;
import com.api.ecommerce.entity.Review;
import com.api.ecommerce.entity.User;
import com.api.ecommerce.repo.ReviewRepo;
import com.api.ecommerce.response.Response;
import com.api.ecommerce.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewRepo repo;
	
	@Autowired
	private CommonService service;
	
	public ResponseEntity<?> retrieveAllReviews() {
		Response<List<ReviewResponseDTO>> response = new Response<>();
		
		List<Review> reviews = repo.findAll();
		
		if(reviews == null || reviews.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No Reviews Added!!");
			return ResponseEntity.ok().body(response);
		}
		
		response.setSuccess(true);
		response.setMessage("Reviews Retrieved Successfully!!");
		response.setData(reviews.stream().map(review -> service.convertReviewToReviewResponseDTO(review)).toList());
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> retrieveReviewsGivenByUser(String userExternalId) {
		Response<List<ReviewResponseDTO>> response = new Response<>();
		
		User user = service.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_USER_FOUND);
			return ResponseEntity.ok().body(response);
		}
		
		List<Review> reviews = repo.findByUser_EntityId(user.getEntityId());
		
		if(reviews == null || reviews.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No Reviews Added By User!!");
			return ResponseEntity.ok().body(response);
		}
		
		response.setSuccess(true);
		response.setMessage("Reviews Retrieved Successfully Given By User!!");
		response.setData(reviews.stream().map(review -> service.convertReviewToReviewResponseDTO(review)).toList());
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> retrieveReviewsGivenToProduct(String productExternalId) {
		Response<ProductReviewResponseDTO> response = new Response<>();
		
		Product product = service.findProductByExternalId(productExternalId);
		
		if(product == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_PRODUCT_FOUND);
			return ResponseEntity.ok().body(response);
		}
		
		List<Review> productReviews = repo.findByProduct_EntityId(product.getEntityId());
		
		if(productReviews == null || productReviews.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No Reviews Added to Product!!");
			return ResponseEntity.ok().body(response);
		}
		
		response.setSuccess(true);
		response.setMessage("Reviews Retrieved Successfully Given To Product!!");
		response.setData(service.convertReviewToProductReviewResponseDTO(productReviews, product));
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> addReviewToProductByUser(ReviewRequestDTO reviewDto) {
		Response<ReviewResponseDTO> response = new Response<>();
		
		Review review = service.convertReviewRequestDtoToReview(reviewDto);
		
		repo.save(review);
		
		response.setSuccess(true);
		response.setMessage("Reviews Added Successfully!!");
		response.setData(service.convertReviewToReviewResponseDTO(review));
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> updateReviewToProductByUser(String reviewExternalId, ReviewRequestDTO reviewDto) {
		Response<ReviewResponseDTO> response = new Response<>();
		
		Optional<Review> reviewFound = repo.findByExternalId(reviewExternalId);
		
		if(reviewFound.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("Review not found!!");
			return ResponseEntity.ok().body(response);
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
		response.setData(service.convertReviewToReviewResponseDTO(review));
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> deleteReview(String reviewExternalId) {
		Response<ReviewResponseDTO> response = new Response<>();
		
		Optional<Review> review = repo.findByExternalId(reviewExternalId);
		
		if(review.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("Review not found!!");
			return ResponseEntity.ok().body(response);
		}
		
		repo.deleteById(review.get().getEntityID());

		response.setSuccess(true);
		response.setMessage("Review deleted Successfully!!");
		
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<?> retrieveReviewByExternalId(String reviewExternalId) {
		Response<ReviewResponseDTO> response = new Response<>();
		
		Optional<Review> review = repo.findByExternalId(reviewExternalId);
		
		if(review.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("Review not found!!");
			return ResponseEntity.ok().body(response);
		}
		
		response.setSuccess(true);
		response.setMessage("Review retrieved Successfully!!");
		response.setData(service.convertReviewToReviewResponseDTO(review.get()));
		return ResponseEntity.ok().body(response);
	}

}
