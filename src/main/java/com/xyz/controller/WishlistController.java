package com.xyz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.models.Product;
import com.xyz.models.User;
import com.xyz.models.Wishlist;
import com.xyz.repository.WishlistRepository;
import com.xyz.service.ProductService;
import com.xyz.service.UserService;
import com.xyz.service.WishlistService;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
	
	
	@Autowired
	private WishlistService wishlistService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	
	
	
	
	@GetMapping("/")
	public ResponseEntity<Wishlist> getUserWishList(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwt(jwt);
		Wishlist wishlist = wishlistService.getUserWishlist(user);
		return new ResponseEntity<Wishlist>(wishlist,HttpStatus.OK);
	}

	
	@PutMapping("/add-remove-product/{productId}")
	public ResponseEntity<Wishlist> addRemoveProductToWishlist(@RequestHeader("Authorization") String jwt, @PathVariable Long productId) throws Exception{
		User user = userService.findUserByJwt(jwt);
		Product product = productService.findProductById(productId);
		Wishlist wishlist = wishlistService.addRemoveProductFromWishlist(user, product);
		return new ResponseEntity<Wishlist>(wishlist,HttpStatus.OK);
	}


}
