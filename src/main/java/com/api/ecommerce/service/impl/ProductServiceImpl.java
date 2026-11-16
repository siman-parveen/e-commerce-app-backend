package com.api.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.api.ecommerce.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.ProductRequestDTO;
import com.api.ecommerce.dto.response.ProductResponseDTO;
import com.api.ecommerce.dto.response.ProductUserBroughtResponseDTO;
import com.api.ecommerce.repo.ProductRepo;
import com.api.ecommerce.response.Response;
import com.api.ecommerce.service.ProductService;
import org.springframework.util.CollectionUtils;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepo repo;
	
	@Autowired
	private CommonService commonService;

	public ProductServiceImpl(ProductRepo repo) {
		this.repo = repo;
	}

	public Response<?> retrieveAllProducts(){
		Response<List<ProductResponseDTO>> response = new Response<>();
				
		List<Product> products = repo.findAll().stream()
				.filter(Product::isActive)
				.toList();
		
		if(CollectionUtils.isEmpty(products)) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_PRODUCTS_FOUND);
			return response;
		}
		
		List<ProductResponseDTO> productDTOList = products.stream().map((product) -> commonService.convertProductToProductResponseDTO(product)).toList();
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.RETRIEVE_ALL_PRODUCTS_SUCCESS);
		response.setResponseData(productDTOList);
		
		return response;
	}

	public Response<?> retrieveProductById(String productExternalId) {
		Response<ProductResponseDTO> response = new Response<>();
		
		Product product = commonService.findProductByExternalId(productExternalId);
		
		if(product == null || !product.isActive()) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_PRODUCT_FOUND);
			return response;
		}
		
		ProductResponseDTO productDto = commonService.convertProductToProductResponseDTO(product);
		
		response.setMessage(CommonConstant.RETRIEVE_PRODUCT_SUCCESS);
		response.setSuccess(true);
		response.setResponseData(productDto);
		
		return response;
	}
	
	public Response<?> addProduct(ProductRequestDTO productDto) {
		
		Response<ProductResponseDTO> response = new Response<>();

		Product product = commonService.convertProductRequestDTOToProduct(productDto);
		repo.save(product);
		
		response.setMessage(CommonConstant.PRODUCT_ADDED_SUCCESS);
		response.setSuccess(true);
		response.setResponseData(commonService.convertProductToProductResponseDTO(product));
		
		return response;
	}
	
	public Response<?> updateProduct(String productExternalId, ProductRequestDTO productDto) {
		
		Response<ProductResponseDTO> response = new Response<>();
			
		Product product = commonService.findProductByExternalId(productExternalId);
		
		if(product == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_PRODUCT_FOUND);
			return response;
		}

		if (!product.isActive()) {
			response.setSuccess(false);
			response.setMessage("Inactive Product.");
			return response;
		}
		
		if(productDto.getProductName() != null && commonService.isUpdated(product.getProductName(), productDto.getProductName())) {
			product.setProductName(productDto.getProductName());
		}
		
		if(productDto.getDescription() != null && commonService.isUpdated(product.getDescription(), productDto.getDescription())) {
			product.setDescription(productDto.getDescription());
		}
		
		if(productDto.getPrice() != null && commonService.isUpdated(product.getPrice(), productDto.getPrice())) {
			product.setPrice(productDto.getPrice());
		}
		
		if(productDto.getStock() != null && commonService.isUpdated(product.getStock(), productDto.getStock())) {
			product.setStock(productDto.getStock());
		}

		if(productDto.getDiscountPercentage() != null && commonService.isUpdated(product.getDiscountPercentage(), productDto.getDiscountPercentage())) {
			product.setDiscountPercentage(productDto.getDiscountPercentage());
		}
		
		repo.save(product);
		
		response.setMessage(CommonConstant.PRODUCT_UPDATE_SUCCESS);
		response.setSuccess(true);
		response.setResponseData(commonService.convertProductToProductResponseDTO(product));
		
		return response;
	}
	
	@Transactional
	public Response<?> removeProduct(String productExternalId) {

		Response<ProductResponseDTO> response = new Response<>();

		Product product = commonService.findProductByExternalId(productExternalId);
		
		if(product == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_PRODUCT_FOUND);
			return response;
		}

		product.setActive(false);
		repo.save(product);

		response.setSuccess(true);
		response.setMessage(CommonConstant.PRODUCT_DELETE_SUCCESS);
		response.setResponseData(commonService.convertProductToProductResponseDTO(product));
		
		return response;
	}

	public Response<?> retrieveProductsBroughtByUser(String userExternalId) {
		Response<List<ProductUserBroughtResponseDTO>> response = new Response<>();
		
		User user = commonService.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage("User with given id not found!!");
			return response;
		}
					
		List<ProductUserBroughtResponseDTO> productDTOList = new ArrayList<>();

        for (Order order : user.getOrders()) {
			if (order.getOrderStatus() == OrderStatus.CANCELLED || order.getOrderStatus() == OrderStatus.RETURNED) {
				continue;
			}
            for (OrderItem orderItem : order.getOrderItems()) {
                productDTOList.add(commonService.createProductUserBroughtResponseDTO(null, user, orderItem));
            }
        }

        for (Review review : user.getReviews()) {
            productDTOList.forEach(dto -> {
                if (review.getProduct().getExternalId().equals(dto.getProductExternalId())) {
                    dto.setRatingExternalId(review.getExternalId());
                    dto.setRating(review.getRating());
                    dto.setComment(review.getComment());
                    dto.setReviewedAt(review.getReviewedAt());
                }
            });
        }


        if(productDTOList.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No Products brought by the user yet!!");
			return response;
		}
		
		response.setSuccess(true);
		response.setMessage("All Products brought by the user are fetched successfully!!");
		response.setResponseData(productDTOList);
		
		return response;
	}
	
}
