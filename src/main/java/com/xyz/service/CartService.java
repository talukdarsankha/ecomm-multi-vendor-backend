package com.xyz.service;

import com.xyz.models.Cart;
import com.xyz.models.CartItem;
import com.xyz.models.Product;
import com.xyz.models.User;

public interface CartService {
	
	public CartItem addItemToCart(User user, Product product, String size, int quantity);
	
	public Cart findUserCartForUserCartPage(User user);
	
	public Cart findUserCart(User user);


}
