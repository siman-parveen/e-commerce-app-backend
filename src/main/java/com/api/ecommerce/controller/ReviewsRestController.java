package com.api.ecommerce.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
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

import com.api.ecommerce.dto.request.ReviewRequestDTO;
import com.api.ecommerce.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name = "Reviews API", description = "Operations related to reviews given to the E-commerce product")
public class ReviewsRestController {
	
	@Autowired
	private ReviewService service;
	
	@Operation(summary = "Retrieve All Review", description = "API returns all the reviews given by all user")
	@GetMapping("/admin/reviews")
	public ResponseEntity<?> retrieveAllReviews() {
		return service.retrieveAllReviews();
	}
	
	@Operation(summary = "Retrieve Review given by user", description = "API returns all the reviews given by a user")
	@GetMapping("/user/{userExternalId}/reviews")
	public ResponseEntity<?> retrieveReviewsGivenByUser(@PathVariable String userExternalId) {
		return service.retrieveReviewsGivenByUser(userExternalId);
	}
	
	@Operation(summary = "Retrieve Review given to  product", description = "API returns all the reviews given to a product")
	@GetMapping("/product/{productExternalId}/reviews")
	public ResponseEntity<?> retrieveReviewsGivenToProduct(@PathVariable String productExternalId) {
		return service.retrieveReviewsGivenToProduct(productExternalId);
	}

	@Operation(summary = "Add a review to a product", description = "API adds a review to a product by a user")
	@PostMapping("/user/product/review")
	public ResponseEntity<?> addReviewToProductByUser(@Valid @RequestBody ReviewRequestDTO review) {
		return service.addReviewToProductByUser(review);
	}

	@Operation(summary = "Update the review to the product", description = "API updates the review to the product by a user")
	@PatchMapping("/user/product/review/{reviewExternalId}")
	public ResponseEntity<?> updateReviewToProductByUser(@PathVariable String reviewExternalId, @Valid @RequestBody ReviewRequestDTO review) {
		return service.updateReviewToProductByUser(reviewExternalId, review);
	}
	
	@Operation(summary = "Delete the review to the product", description = "API deletes the review")
	@DeleteMapping("/user/product/review/{reviewExternalId}")
	public ResponseEntity<?> deleteReview(@PathVariable String reviewExternalId){
		return service.deleteReview(reviewExternalId);
	}
	
	@Operation(summary = "Retrieve Review by External ID", description = "API retrieves the review by the given external id")
	@GetMapping("/public/product/review/{reviewExternalId}")
	public ResponseEntity<?> retrieveReviewByExternalId(@PathVariable String reviewExternalId){
		return service.retrieveReviewByExternalId(reviewExternalId);
	}

}
