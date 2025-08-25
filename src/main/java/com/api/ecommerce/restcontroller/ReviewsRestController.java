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

import com.api.ecommerce.dto.request.ReviewRequestDTO;
import com.api.ecommerce.dto.response.ReviewResponseDTO;
import com.api.ecommerce.dto.response.UserResponseDTO;
import com.api.ecommerce.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ReviewsRestController {
	
	@Autowired
	private ReviewService service;
	
	@Operation(summary = "Retrieve All Review", description = "API returns all the registered users")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Reviews retrieved successfully!!", content = @Content(mediaType = "/application/json", array = @ArraySchema(schema = @Schema(implementation = ReviewResponseDTO.class)))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@GetMapping("/admin/reviews")
	public ResponseEntity<?> retrieveAllReviews() {
		return service.retrieveAllReviews();
	}
	
	@Operation(summary = "Retrieve Review given by user", description = "API returns the registered user by the External ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "User with given id retrieved successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = UserResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@GetMapping("/user/{userExternalId}/reviews")
	public ResponseEntity<?> retrieveReviewsGivenByUser(@PathVariable String userExternalId) {
		return service.retrieveReviewsGivenByUser(userExternalId);
	}
	
	@Operation(summary = "Retrieve Review given to  product", description = "API returns the registered user by the External ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "User with given id retrieved successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = UserResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@GetMapping("/product/{productExternalId}/reviews")
	public ResponseEntity<?> retrieveReviewsGivenToProduct(@PathVariable String productExternalId) {
		return service.retrieveReviewsGivenToProduct(productExternalId);
	}
	
	
	@Operation(summary = "Register a new user", description = "API registers the user and returns the same user")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Users added successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = UserResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@PostMapping("/user/product/review")
	public ResponseEntity<?> addReviewToProductByUser(@Valid @RequestBody ReviewRequestDTO review) {
		return service.addReviewToProductByUser(review);
	}
	
	
	@Operation(summary = "Register a new user", description = "API registers the user and returns the same user")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Users added successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = UserResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@PatchMapping("/user/product/review/{reviewExternalId}")
	public ResponseEntity<?> updateReviewToProductByUser(@PathVariable String reviewExternalId, @Valid @RequestBody ReviewRequestDTO review) {
		return service.updateReviewToProductByUser(reviewExternalId, review);
	}
	
	@Operation(summary = "De-register a user by ID", description = "API deletes the user and returns a success delete message")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Users deleted successfully!!", content = @Content(mediaType = "/application/json")),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@DeleteMapping("/user/product/review/{reviewExternalId}")
	public ResponseEntity<?> deleteReview(@PathVariable String reviewExternalId){
		return service.deleteReview(reviewExternalId);
	}

}
