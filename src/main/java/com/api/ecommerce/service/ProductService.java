package com.api.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.api.ecommerce.dto.request.ProductRequestDTO;

public interface ProductService {
	
	public ResponseEntity<?> retrieveAllProducts();
	public ResponseEntity<?> retrieveProductById(String productExternalId);
	public ResponseEntity<?> addProduct(ProductRequestDTO productDto);
	public ResponseEntity<?> updateProduct(String productExternalId, ProductRequestDTO productDto);
	public ResponseEntity<?> removeProduct(String productExternalId);
	public ResponseEntity<?> retrieveProductsBroughtByUser(String userExternalId);
}
