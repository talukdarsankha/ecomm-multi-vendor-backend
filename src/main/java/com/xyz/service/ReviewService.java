package com.xyz.service;

import java.util.List;

import com.xyz.Request.CreateReviewRequest;
import com.xyz.models.Product;
import com.xyz.models.Review;
import com.xyz.models.User;

public interface ReviewService {
	
	public Review createReview(CreateReviewRequest createReviewRequest, User user, Product product);  
	
	public List<Review> getProductAllReview(Long productId);
	
	public Review updateReview(Long reviewId,String reviewText,double rating,Long userId) throws Exception;
	
	public void deleteReview(Long reviewId, Long userId) throws Exception;
	
	public Review getReviewById(Long reviewId);
	

}
