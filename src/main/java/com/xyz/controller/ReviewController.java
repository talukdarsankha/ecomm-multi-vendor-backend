package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Request.CreateReviewRequest;
import com.xyz.Response.ApiResponse;
import com.xyz.models.Product;
import com.xyz.models.Review;
import com.xyz.models.User;
import com.xyz.service.ProductService;
import com.xyz.service.ReviewService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
	
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	

	
	
	@PostMapping("/product/{productId}")
	public ResponseEntity<Review> createReview( @RequestBody CreateReviewRequest request, @RequestHeader("Authorization") String jwt, @PathVariable("productId") Long productId) throws Exception{
		User user = userService.findUserByJwt(jwt);
		Product product = productService.findProductById(productId);
		
		Review review = reviewService.createReview(request, user, product);
		return new ResponseEntity<Review>(review, HttpStatus.CREATED);
	}
	
	
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getProductReviews(@PathVariable("productId") Long productId){
		List<Review> reviews = reviewService.getProductAllReview(productId);
		return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
	}
	
	@PutMapping("/{reviewId}")
	public ResponseEntity<Review> updateReviews(@RequestBody CreateReviewRequest request,@RequestHeader("Authorization") String jwt, @PathVariable("reviewId") Long reviewId) throws Exception{
		User user = userService.findUserByJwt(jwt);
		Review review = reviewService.updateReview(reviewId, request.getReviewText(), request.getRating(), user.getId());
		return new ResponseEntity<Review>(review, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<ApiResponse> deleteReviews(@RequestBody CreateReviewRequest request,@RequestHeader("Authorization") String jwt, @PathVariable("reviewId") Long reviewId) throws Exception{
		User user = userService.findUserByJwt(jwt);
		 reviewService.deleteReview(reviewId, user.getId());
		return new ResponseEntity<ApiResponse>(new ApiResponse("Your Review Deleted Successfully..."), HttpStatus.OK);
	}
	
	

}
