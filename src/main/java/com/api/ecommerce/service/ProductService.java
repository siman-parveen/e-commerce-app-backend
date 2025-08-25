package com.api.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.ProductRequestDTO;
import com.api.ecommerce.dto.response.ProductResponseDTO;
import com.api.ecommerce.entity.Product;
import com.api.ecommerce.repo.ProductRepo;
import com.api.ecommerce.response.Response;

@Service
public class ProductService {

	private ProductRepo repo;
	
	@Autowired
	private CommonService commonService;

	public ProductService(ProductRepo repo) {
		this.repo = repo;
	}

	public ResponseEntity<?> retrieveAllProducts(){
		Response<List<ProductResponseDTO>> response = new Response<List<ProductResponseDTO>>();
				
		List<Product> products = repo.findAll();
		
		if(products == null || products.isEmpty()) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_PRODUCTS_FOUND);
			return ResponseEntity.ok().body(response);
		}
		
		List<ProductResponseDTO> productDTOList = products.stream().map((product) -> commonService.convertProductToProductResponseDTO(product)).toList();
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.RETRIEVE_ALL_PRODUCTS_SUCCESS);
		response.setData(productDTOList);
		
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> retrieveProductById(String productExternalId) {
		Response<ProductResponseDTO> response = new Response<ProductResponseDTO>();
		
		Product product = commonService.findProductByExternalId(productExternalId);
		
		if(product == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_PRODUCT_FOUND);
			return ResponseEntity.ok().body(response);
		}
		
		ProductResponseDTO productDto = commonService.convertProductToProductResponseDTO(product);
		
		response.setMessage(CommonConstant.RETRIVE_PRODUCT_SUCCESS);
		response.setSuccess(true);
		response.setData(productDto);
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> addProduct(ProductRequestDTO productDto) {
		
		Response<ProductResponseDTO> response = new Response<ProductResponseDTO>();

		Product product = commonService.convertProductRequestDTOToProduct(productDto);
		repo.save(product);
		
		response.setMessage(CommonConstant.PRODUCT_ADDED_SUCCESS);
		response.setSuccess(true);
		response.setData(commonService.convertProductToProductResponseDTO(product));
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> updateProduct(String productExternalId, ProductRequestDTO productDto) {
		
		Response<ProductResponseDTO> response = new Response<ProductResponseDTO>();
			
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
		response.setData(commonService.convertProductToProductResponseDTO(product));
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> removeProduct(String productExternalId) {
		
		
		Response<ProductResponseDTO> response = new Response<ProductResponseDTO>();

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
	
}
