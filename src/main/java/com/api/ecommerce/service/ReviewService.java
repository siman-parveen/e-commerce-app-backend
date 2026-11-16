package com.api.ecommerce.service;

import com.api.ecommerce.response.Response;


import com.api.ecommerce.dto.request.ReviewRequestDTO;

public interface ReviewService {
	
	public Response<?> retrieveAllReviews();
	public Response<?> retrieveReviewsGivenByUser(String userExternalId);
	public Response<?> retrieveReviewsGivenToProduct(String productExternalId);
	public Response<?> addReviewToProductByUser(ReviewRequestDTO reviewDto);
	public Response<?> updateReviewToProductByUser(String reviewExternalId, ReviewRequestDTO reviewDto);
	public Response<?> deleteReview(String reviewExternalId);
	public Response<?> retrieveReviewByExternalId(String reviewExternalId);

}
