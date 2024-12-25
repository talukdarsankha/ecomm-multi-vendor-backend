package com.xyz.service;

import com.xyz.models.Product;
import com.xyz.models.User;
import com.xyz.models.Wishlist;

public interface WishlistService {
	
	public Wishlist createWishlist(User user);
	
	public Wishlist getUserWishlist(User user);
	
	public Wishlist addRemoveProductFromWishlist(User user, Product product);

}
