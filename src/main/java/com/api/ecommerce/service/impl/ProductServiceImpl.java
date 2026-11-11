package com.api.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.api.ecommerce.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

	public ResponseEntity<?> retrieveAllProducts(){
		Response<List<ProductResponseDTO>> response = new Response<>();
				
		List<Product> products = repo.findAll();
		
		if(CollectionUtils.isEmpty(products)) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_PRODUCTS_FOUND);
			return ResponseEntity.ok().body(response);
		}
		
		List<ProductResponseDTO> productDTOList = products.stream().map((product) -> commonService.convertProductToProductResponseDTO(product)).toList();
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.RETRIEVE_ALL_PRODUCTS_SUCCESS);
		response.setResponseData(productDTOList);
		
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> retrieveProductById(String productExternalId) {
		Response<ProductResponseDTO> response = new Response<>();
		
		Product product = commonService.findProductByExternalId(productExternalId);
		
		if(product == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_PRODUCT_FOUND);
			return ResponseEntity.ok().body(response);
		}
		
		ProductResponseDTO productDto = commonService.convertProductToProductResponseDTO(product);
		
		response.setMessage(CommonConstant.RETRIEVE_PRODUCT_SUCCESS);
		response.setSuccess(true);
		response.setResponseData(productDto);
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> addProduct(ProductRequestDTO productDto) {
		
		Response<ProductResponseDTO> response = new Response<>();

		Product product = commonService.convertProductRequestDTOToProduct(productDto);
		repo.save(product);
		
		response.setMessage(CommonConstant.PRODUCT_ADDED_SUCCESS);
		response.setSuccess(true);
		response.setResponseData(commonService.convertProductToProductResponseDTO(product));
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> updateProduct(String productExternalId, ProductRequestDTO productDto) {
		
		Response<ProductResponseDTO> response = new Response<>();
			
		Product product = commonService.findProductByExternalId(productExternalId);
		
		if(product == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_PRODUCT_FOUND);
			return ResponseEntity.ok().body(response);
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
		
		repo.save(product);
		
		response.setMessage(CommonConstant.PRODUCT_UPDATE_SUCCESS);
		response.setSuccess(true);
		response.setResponseData(commonService.convertProductToProductResponseDTO(product));
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> removeProduct(String productExternalId) {
		
		
		Response<ProductResponseDTO> response = new Response<>();

		Product product = commonService.findProductByExternalId(productExternalId);
		
		if(product == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_PRODUCT_FOUND);
			return ResponseEntity.ok().body(response);
		}
		
		repo.deleteById(product.getEntityId());
		response.setMessage(CommonConstant.PRODUCT_DELETE_SUCCESS);
		response.setSuccess(true);
		
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> retrieveProductsBroughtByUser(String userExternalId) {
		Response<List<ProductUserBroughtResponseDTO>> response = new Response<>();
		
		User user = commonService.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage("User with given id not found!!");
			return ResponseEntity.ok().body(response);
		}
					
		List<ProductUserBroughtResponseDTO> productDTOList = new ArrayList<>();

        for (Order order : user.getOrders()) {
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
			return ResponseEntity.ok().body(response);
		}
		
		response.setSuccess(true);
		response.setMessage("All Products brought by the user are fetched successfully!!");
		response.setResponseData(productDTOList);
		
		return ResponseEntity.ok().body(response);
	}
	
}
