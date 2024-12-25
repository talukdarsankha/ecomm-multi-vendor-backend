package com.xyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.models.UserOrder;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Long> {
	
	public List<UserOrder> findByUserId(Long userId);
	
	public List<UserOrder> findBySellerId(Long sellerId);
	
}
