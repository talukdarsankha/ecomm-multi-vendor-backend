package com.xyz.controller;

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

import com.xyz.Request.AddCartItemRequest;
import com.xyz.Response.ApiResponse;
import com.xyz.models.Cart;
import com.xyz.models.CartItem;
import com.xyz.models.Product;
import com.xyz.models.User;
import com.xyz.service.CartItemService;
import com.xyz.service.CartService;
import com.xyz.service.ProductService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	
	
       //	in this api getusercart there is some issue in response.data cart.cartitem is empty array that's why somewhere we manually find cartItems using user cart   
	@GetMapping("/")
	public ResponseEntity<Cart> getUserCart(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwt(jwt);
		Cart cart = cartService.findUserCartForUserCartPage(user);
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<CartItem> addCartItem(@RequestBody AddCartItemRequest addCartItemRequest, @RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwt(jwt);
		Product product = productService.findProductById(addCartItemRequest.getProductId());
		CartItem cartItem = cartService.addItemToCart(user, product, addCartItemRequest.getSize(),addCartItemRequest.getQuantity());
		
		return new ResponseEntity<CartItem>(cartItem, HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/cartitem/{cartitemId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartitemId, @RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartitemId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("item deleted from cart ...");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	
	@PutMapping("/cartitem/{cartitemId}")
	public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartitemId, @RequestBody CartItem cartItem, @RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwt(jwt);
		
		CartItem updatedCartItem = null;
		if (cartItem.getQuantity()>0) {
		 updatedCartItem=cartItemService.updateCartItem(user.getId(), cartitemId, cartItem);
		}
        
		return new ResponseEntity<CartItem>(updatedCartItem, HttpStatus.OK);
			
	}
	
	
	
	
	
	

}
