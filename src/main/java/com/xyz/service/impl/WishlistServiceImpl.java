package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.xyz.models.Product;
import com.xyz.models.User;
import com.xyz.models.Wishlist;
import com.xyz.repository.WishlistRepository;
import com.xyz.service.WishlistService;


@Service
public class WishlistServiceImpl implements WishlistService {
	
	@Autowired
	private WishlistRepository wishlistRepository;

	
	
	@Override
	public Wishlist createWishlist(User user) {
		// TODO Auto-generated method stub
		Wishlist wishlist = new Wishlist();
		wishlist.setUser(user);
		return wishlistRepository.save(wishlist);
	}

	@Override
	public Wishlist getUserWishlist(User user) {
		// TODO Auto-generated method stub
		
		Wishlist wishlist = wishlistRepository.findByUserId(user.getId());
		if (wishlist==null) {
			wishlist = createWishlist(user);
		}
		
		return wishlist;
	}

	@Override
	public Wishlist addRemoveProductFromWishlist(User user, Product product) {
		// TODO Auto-generated method stub
		Wishlist wishlist = getUserWishlist(user);
		if (wishlist.getProducts().contains(product)) {
			wishlist.getProducts().remove(product);
		}else {
			wishlist.getProducts().add(product);
		}
		return wishlistRepository.save(wishlist);
	}
	
	
	
	
	
	
	

}
