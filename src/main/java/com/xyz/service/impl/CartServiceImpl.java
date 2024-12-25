package com.xyz.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.xyz.models.Cart;
import com.xyz.models.CartItem;
import com.xyz.models.Product;
import com.xyz.models.User;
import com.xyz.repository.CartItemReposiroty;
import com.xyz.repository.CartRepository;
import com.xyz.service.CartService;


@Service
public class CartServiceImpl implements CartService {
	
	
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemReposiroty cartItemReposiroty;
	

	
	@Override
	public CartItem addItemToCart(User user, Product product, String size, int quantity) {
	    Cart cart = findUserCart(user);
	    
	    CartItem existCartItem = cartItemReposiroty.findByCartAndProductAndSize(cart, product, size);
	    if (existCartItem == null) {
	        CartItem cartItem = new CartItem();
	        cartItem.setProduct(product);
	        cartItem.setQuantity(quantity);
	        cartItem.setSize(size);
	        cartItem.setUserId(user.getId());
	        
	        int totalSellingPrice = quantity * product.getSellingPrice();
	        int totalMrpPrice = quantity * product.getMrpPrice();
	        
	        cartItem.setMrpPrice(totalMrpPrice);
	        cartItem.setSellingPrice(totalSellingPrice);
	        
	        cartItem.setCart(cart);
	        // Save cartItem, Hibernate will update the cart automatically
	        return cartItemReposiroty.save(cartItem);
	    }
	    
	    return existCartItem;
	}

	@Override
	public Cart findUserCartForUserCartPage(User user) {
		// TODO Auto-generated method stub
		Cart cart = cartRepository.findByUserId(user.getId());
		
		Set<CartItem> allCartItems = cartItemReposiroty.findByCart(cart);
		 
		cart.setCartItems(allCartItems);
		
		int totalItem = 0;
		int totalDiscountedPrice = 0;
		int totalPrice = 0;
		
		for(CartItem item: cart.getCartItems()) {
			totalItem += item.getQuantity();
			totalPrice+=item.getMrpPrice();
			totalDiscountedPrice+=item.getSellingPrice();
		}
		
		cart.setTotalItem(totalItem);
		cart.setTotalMrpPrice(totalPrice);
		cart.setTotalSellingPrice(totalDiscountedPrice);
		cart.setTotalDiscount(calculateDiscountPercent(totalPrice, totalDiscountedPrice));
		
		return cart;
	}
	
	
	@Override
	public Cart findUserCart(User user) {
		// TODO Auto-generated method stub
		Cart cart = cartRepository.findByUserId(user.getId());
		
//		Set<CartItem> allCartItems = cartItemReposiroty.findByCart(cart);
//		 
//		cart.setCartItems(allCartItems);
		
		int totalItem = 0;
		int totalDiscountedPrice = 0;
		int totalPrice = 0;
		
		for(CartItem item: cart.getCartItems()) {
			totalItem += item.getQuantity();
			totalPrice+=item.getMrpPrice();
			totalDiscountedPrice+=item.getSellingPrice();
		}
		
		cart.setTotalItem(totalItem);
		cart.setTotalMrpPrice(totalPrice);
		cart.setTotalSellingPrice(totalDiscountedPrice);
		cart.setTotalDiscount(calculateDiscountPercent(totalPrice, totalDiscountedPrice));
		
		return cart;
	}
	
	
	
	private int calculateDiscountPercent(int totalPrice, int totalDiscountedPrice) {
		if (totalPrice<=0) {
			return 0;
//			throw new IllegalArgumentException("Actual Price must be greater that 0");
		}
		double totalDiscount = totalPrice-totalDiscountedPrice;
		double totalDiscountPercent = (totalDiscount/totalPrice)*100;
		
		return (int) totalDiscountPercent;
	}


	

}
