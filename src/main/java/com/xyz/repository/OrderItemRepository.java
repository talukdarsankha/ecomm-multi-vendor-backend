package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.models.Category;
import com.xyz.models.OrderItem;
import com.xyz.models.UserOrder;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
