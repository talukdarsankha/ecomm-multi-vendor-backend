package com.xyz.service;

import java.util.List;
import java.util.Set;

import com.xyz.domain.OrderStatus;
import com.xyz.models.Address;
import com.xyz.models.Cart;
import com.xyz.models.CartItem;
import com.xyz.models.OrderItem;
import com.xyz.models.User;
import com.xyz.models.UserOrder;

public interface OrderService {
	
	public Set<UserOrder> createOrder(User user, Long addressId, Cart cart);
	
	public UserOrder findOrderById(Long orderId);
	
	public List<UserOrder> userAllOrders(Long userId);
	
	public List<UserOrder> gertAllOrdersBySeller(Long sellerId);
	
	public UserOrder updateOrderStatus(Long orderId, OrderStatus status);
	
	public UserOrder cancelOrder(Long orderId,Long userId) throws Exception;
	
	public OrderItem findOrderItemById(Long orderItemId);

}
