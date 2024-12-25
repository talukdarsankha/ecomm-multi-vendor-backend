package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.Exception.CartItemException;
import com.xyz.models.CartItem;
import com.xyz.models.User;
import com.xyz.repository.CartItemReposiroty;
import com.xyz.repository.CartRepository;
import com.xyz.service.CartItemService;


@Service
public class CartItemServiceImpl implements CartItemService {
	
	@Autowired
	private CartItemReposiroty cartItemReposiroty;
	
	
	

	@Override
	public CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws Exception, CartItemException {     
		// TODO Auto-generated method stub
		
		CartItem findCartItem = findCartItemById(cartItemId);
		User cartItemUser = findCartItem.getCart().getUser();
		if (cartItemUser.getId().equals(userId)) {
			findCartItem.setQuantity(cartItem.getQuantity());
			
			findCartItem.setMrpPrice(findCartItem.getQuantity()*findCartItem.getProduct().getMrpPrice());
			findCartItem.setSellingPrice(findCartItem.getQuantity()*findCartItem.getProduct().getSellingPrice());  
		
			return	cartItemReposiroty.save(findCartItem);
		}
		throw new CartItemException("you can't update another user cartitem...");
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, Exception {
		// TODO Auto-generated method stub
		CartItem findCartItem = findCartItemById(cartItemId);
		
		if (findCartItem.getCart().getUser().getId().equals(userId)) {
			cartItemReposiroty.delete(findCartItem);
		}else {
			throw new CartItemException("you can't delete another user cartitem...");
		}
		
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws Exception {
		// TODO Auto-generated method stub
		CartItem cartItem = cartItemReposiroty.findById(cartItemId).orElseThrow(()->new CartItemException("Cartitem Not found..."));   
		return cartItem;
	}

}
