package com.xyz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.Request.CreateReviewRequest;
import com.xyz.models.Product;
import com.xyz.models.Review;
import com.xyz.models.User;
import com.xyz.repository.ProductRepository;
import com.xyz.repository.ReviewRepository;
import com.xyz.service.ProductService;
import com.xyz.service.ReviewService;
import com.xyz.service.UserService;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ReviewRepository reviewRepository;

	
	@Autowired
	private ProductRepository productRepository;

	
	
	
	@Override
	public Review createReview(CreateReviewRequest createReviewRequest, User user, Product product) {
		// TODO Auto-generated method stub
		
		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReviewText(createReviewRequest.getReviewText());
		review.setRating(createReviewRequest.getRating());
		review.setProductimages(createReviewRequest.getProductimages());
		
		review = reviewRepository.save(review);
		product.getReviews().add(review);
		productRepository.save(product);
		
		return review;
	}

	@Override
	public List<Review> getProductAllReview(Long productId) {
		// TODO Auto-generated method stub
		Product product = productService.findProductById(productId);
		return product.getReviews();
	}

	@Override
	public Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception {
		// TODO Auto-generated method stub
		
		Review review = getReviewById(reviewId);
		if (review.getUser().getId().equals(userId)) {
			review.setReviewText(reviewText);
			review.setRating(rating);
			return reviewRepository.save(review);
		}
		throw new Exception("you can't update another user review...");
	}
	
	@Override
	public Review getReviewById(Long reviewId) {
		// TODO Auto-generated method stub
		return reviewRepository.findById(reviewId).get();
	}

	

	@Override
	public void deleteReview(Long reviewId, Long userId) throws Exception {
		// TODO Auto-generated method stub
		Review review = getReviewById(reviewId);
		if (!review.getUser().getId().equals(userId)) {
			throw new Exception("you can't delete another user review...");
		}
		reviewRepository.delete(review);
	}

	
}
