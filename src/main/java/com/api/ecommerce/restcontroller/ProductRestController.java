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

import com.api.ecommerce.entity.Product;
import com.api.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

	@Autowired
	private ProductService service;
	
	@GetMapping
	public ResponseEntity<?> retrieveAllProducts() {
		return service.retrieveAllProducts();
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<?> retrieveProductById(@PathVariable Long productId) {
		return service.retrieveProductById(productId);
	}
	
	@PostMapping
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		return service.addProduct(product);
	}
	
	@PatchMapping("/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
		return service.updateProduct(productId, product);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<?> removeProduct(@PathVariable Long productId){
		return service.removeProduct(productId);
	}
	
}
