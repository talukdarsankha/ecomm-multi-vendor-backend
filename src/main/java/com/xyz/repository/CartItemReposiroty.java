package com.xyz.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.models.Cart;
import com.xyz.models.CartItem;
import com.xyz.models.Product;


@Repository
public interface CartItemReposiroty extends JpaRepository<CartItem, Long> {
	
	public CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);

	public Set<CartItem> findByCart(Cart cart);
}
