package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyz.models.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	
	public Cart findByUserId(Long userId);

}


