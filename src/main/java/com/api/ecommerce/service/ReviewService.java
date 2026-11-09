package com.api.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.api.ecommerce.dto.request.ReviewRequestDTO;

public interface ReviewService {
	
	public ResponseEntity<?> retrieveAllReviews();
	public ResponseEntity<?> retrieveReviewsGivenByUser(String userExternalId);
	public ResponseEntity<?> retrieveReviewsGivenToProduct(String productExternalId);
	public ResponseEntity<?> addReviewToProductByUser(ReviewRequestDTO reviewDto);
	public ResponseEntity<?> updateReviewToProductByUser(String reviewExternalId, ReviewRequestDTO reviewDto);
	public ResponseEntity<?> deleteReview(String reviewExternalId);
	public ResponseEntity<?> retrieveReviewByExternalId(String reviewExternalId);

}
