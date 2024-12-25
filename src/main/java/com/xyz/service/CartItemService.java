package com.xyz.service;

import com.xyz.Exception.CartItemException;
import com.xyz.models.CartItem;

public interface CartItemService {
	
	public CartItem updateCartItem(Long userId, Long cartItemId,CartItem cartItem) throws CartItemException, Exception;
	
	public void removeCartItem(Long userId,Long cartItemId ) throws CartItemException, Exception;
	
	public CartItem findCartItemById(Long cartItemId) throws Exception;

}
