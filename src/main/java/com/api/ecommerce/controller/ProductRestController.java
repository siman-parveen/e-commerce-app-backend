package com.api.ecommerce.controller;

import com.api.ecommerce.response.Response;

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
import jakarta.validation.groups.Default;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api")
@Tag(name = "Product API", description = "Operations related to E-commerce products")
public class ProductRestController {

	@Autowired
	private ProductService service;
	
	@Operation(summary = "Retrieve All Products", description = "API retrieves the all the products.")
	@GetMapping("/public/product")
	public ResponseEntity<?> retrieveAllProducts() {
		Response<?> response = service.retrieveAllProducts();
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Retrieve Product by ID", description = "API retrieves the product by id.")
	@GetMapping("/public/product/{productExternalId}")
	public ResponseEntity<?> retrieveProductById(@PathVariable String productExternalId) {
		Response<?> response = service.retrieveProductById(productExternalId);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Add product", description = "API adds a new product.")
	@PostMapping("/admin/product")
	public ResponseEntity<?> addProduct(
			@Validated({Default.class, ProductRequestDTO.CreateValidation.class}) @RequestBody ProductRequestDTO product) {
		Response<?> response = service.addProduct(product);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Update product", description = "API updates the product.")
	@PatchMapping("/admin/product/{productExternalId}")
	public ResponseEntity<?> updateProduct(@PathVariable String productExternalId, @Valid @RequestBody ProductRequestDTO product) {
		Response<?> response = service.updateProduct(productExternalId, product);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Delete Product", description = "API deletes the product.")
	@DeleteMapping("/admin/product/{productExternalId}")
	public ResponseEntity<?> removeProduct(@PathVariable String productExternalId){
		Response<?> response = service.removeProduct(productExternalId);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Retrieve Products Brought By User", description = "API retrieves the products brought by a user.")
	@GetMapping("/user/{userExternalId}/products")
	@PreAuthorize("@userAuthorization.isCurrentUser(#p0)")
	public ResponseEntity<?> retrieveProductsBroughtByUser(@PathVariable String userExternalId){
		Response<?> response = service.retrieveProductsBroughtByUser(userExternalId);
		return ResponseEntity.ok().body(response);
	}
	
}
