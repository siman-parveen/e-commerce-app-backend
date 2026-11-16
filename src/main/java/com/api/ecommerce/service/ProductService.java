package com.api.ecommerce.service;

import com.api.ecommerce.response.Response;


import com.api.ecommerce.dto.request.ProductRequestDTO;

public interface ProductService {
	
	public Response<?> retrieveAllProducts();
	public Response<?> retrieveProductById(String productExternalId);
	public Response<?> addProduct(ProductRequestDTO productDto);
	public Response<?> updateProduct(String productExternalId, ProductRequestDTO productDto);
	public Response<?> removeProduct(String productExternalId);
	public Response<?> retrieveProductsBroughtByUser(String userExternalId);
}
