package com.api.ecommerce.restcontroller;

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
import com.api.ecommerce.dto.response.ProductResponseDTO;
import com.api.ecommerce.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name = "Product API", description = "Operations related to E-commerce products")
public class ProductRestController {

	@Autowired
	private ProductService service;
	
	@Operation(summary = "Retrieve All Products", description = "API returns the all the products added by admin")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Products retrieved successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@GetMapping("/public/product")
	public ResponseEntity<?> retrieveAllProducts() {
		return service.retrieveAllProducts();
	}
	
	@Operation(summary = "Retrieve Product by ID", description = "API returns the product by the External ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Product with given id retrieved successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@GetMapping("/public/product/{productExternalId}")
	public ResponseEntity<?> retrieveProductById(@PathVariable String productExternalId) {
		return service.retrieveProductById(productExternalId);
	}
	
	@Operation(summary = "Add product", description = "API returns the product that has been added by the admin")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Product added successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@PostMapping("/admin/product")
	public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequestDTO product) {
		return service.addProduct(product);
	}
	
	@Operation(summary = "Update product", description = "API returns the updated product")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Product updated successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@PatchMapping("/admin/product/{productExternalId}")
	public ResponseEntity<?> updateProduct(@PathVariable String productExternalId, @Valid @RequestBody ProductRequestDTO product) {
		return service.updateProduct(productExternalId, product);
	}
	
	@Operation(summary = "Delete Product", description = "API deletes the product by External ID and returns a successful message")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Product removed successfully!!", content = @Content(mediaType = "/application/json")),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@DeleteMapping("/admin/product/{productExternalId}")
	public ResponseEntity<?> removeProduct(@PathVariable String productExternalId){
		return service.removeProduct(productExternalId);
	}
	
}
