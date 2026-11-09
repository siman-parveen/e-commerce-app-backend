package com.api.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.dto.request.ProductRequestDTO;
import com.api.ecommerce.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name = "Product API", description = "Operations related to E-commerce products")
public class ProductRestController {

	@Autowired
	private ProductService service;
	
	@Operation(summary = "Retrieve All Products", description = "API retrieves the all the products.")
	@GetMapping("/public/product")
	public ResponseEntity<?> retrieveAllProducts() {
		return service.retrieveAllProducts();
	}
	
	@Operation(summary = "Retrieve Product by ID", description = "API retrieves the product by id.")
	@GetMapping("/public/product/{productExternalId}")
	public ResponseEntity<?> retrieveProductById(@PathVariable String productExternalId) {
		return service.retrieveProductById(productExternalId);
	}
	
	@Operation(summary = "Add product", description = "API adds a new product.")
	@PostMapping("/admin/product")
	public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequestDTO product) {
		return service.addProduct(product);
	}
	
	@Operation(summary = "Update product", description = "API updates the product.")
	@PatchMapping("/admin/product/{productExternalId}")
	public ResponseEntity<?> updateProduct(@PathVariable String productExternalId, @Valid @RequestBody ProductRequestDTO product) {
		return service.updateProduct(productExternalId, product);
	}
	
	@Operation(summary = "Delete Product", description = "API deletes the product.")
	@DeleteMapping("/admin/product/{productExternalId}")
	public ResponseEntity<?> removeProduct(@PathVariable String productExternalId){
		return service.removeProduct(productExternalId);
	}
	
	@Operation(summary = "Retrieve Products Brought By User", description = "API retrieves the products brought by a user.")
	@GetMapping("/user/{userExternalId}/products")
	public ResponseEntity<?> retrieveProductsBroughtByUser(@PathVariable String userExternalId){
		return service.retrieveProductsBroughtByUser(userExternalId);
	}
	
}
