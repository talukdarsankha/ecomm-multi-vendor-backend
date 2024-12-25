package com.xyz.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.domain.OrderStatus;
import com.xyz.domain.PaymentStatus;
import com.xyz.models.Address;
import com.xyz.models.Cart;
import com.xyz.models.CartItem;
import com.xyz.models.OrderItem;
import com.xyz.models.User;
import com.xyz.models.UserOrder;
import com.xyz.repository.AddressRepository;
import com.xyz.repository.CartItemReposiroty;
import com.xyz.repository.OrderItemRepository;
import com.xyz.repository.OrderRepository;
import com.xyz.repository.UserRepository;
import com.xyz.service.CartService;
import com.xyz.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private CartItemReposiroty cartItemReposiroty;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	

	@Override
	public Set<UserOrder> createOrder(User user, Long addressId, Cart cart) {
		// TODO Auto-generated method stub
		
		Address shippingAddress = addressRepository.findById(addressId).get();
		
		Set<CartItem> cartItms = cartItemReposiroty.findByCart(cart);
		
		Map<Long, List<CartItem>> itemsBySeller = cartItms.stream().
				collect(Collectors.groupingBy(item->item.getProduct().getSeller().getId()));  
		
		Set<UserOrder> orders = new HashSet<>();
		
		for(Map.Entry<Long, List<CartItem>> e: itemsBySeller.entrySet()) {
			Long sellerId = e.getKey();
			List<CartItem> cartItems  = e.getValue();
			
			int totalMrpPrice = cartItems.stream().mapToInt(CartItem::getMrpPrice).sum();
			int totalSellingPrice = cartItems.stream().mapToInt(CartItem::getSellingPrice).sum();
			int totalItem= cartItems.stream().mapToInt(CartItem::getQuantity).sum();
			
			UserOrder createOrder = new UserOrder();
			createOrder.setUser(user);
			createOrder.setSellerId(sellerId);
			createOrder.setTotalItem(totalItem);
			createOrder.setTotalMrpPrice(totalMrpPrice);
			createOrder.setTotalSellingPrice(totalSellingPrice);
			createOrder.setShippingAddress(shippingAddress);
			createOrder.setOrderStatus(OrderStatus.PENDING);
			createOrder.getPaymentDetails().setPaymentStatus(PaymentStatus.PENDING);
			
			createOrder = orderRepository.save(createOrder);
						
			for(CartItem item: cartItems) {
				OrderItem orderItem = new OrderItem();
				
				orderItem.setOrder(createOrder);
				orderItem.setMrpPrice(item.getMrpPrice());
				orderItem.setSellingPrice(item.getSellingPrice());
				orderItem.setProduct(item.getProduct());
				orderItem.setQuantity(item.getQuantity());
				orderItem.setSize(item.getSize());
				orderItem.setUserId(item.getUserId());
				
				OrderItem savedOrderItem = orderItemRepository.save(orderItem);
				
				createOrder.getOrderItems().add(savedOrderItem);
			}
			
			createOrder =  orderRepository.save(createOrder);
			orders.add(createOrder);
			
 		}
		
		
		return orders;
	}

	@Override
	public UserOrder findOrderById(Long orderId) {
		// TODO Auto-generated method stub
		return orderRepository.findById(orderId).get();
	}

	@Override
	public List<UserOrder> userAllOrders(Long userId) {
		// TODO Auto-generated method stub
		return orderRepository.findByUserId(userId);
	}

	@Override
	public List<UserOrder> gertAllOrdersBySeller(Long sellerId) {
		// TODO Auto-generated method stub
		return orderRepository.findBySellerId(sellerId);
	}

	@Override
	public UserOrder updateOrderStatus(Long orderId,OrderStatus status) {
		// TODO Auto-generated method stub
		UserOrder order = findOrderById(orderId);
		order.setOrderStatus(status);
		
		return orderRepository.save(order);
	}

	@Override
	public UserOrder cancelOrder(Long orderId, Long userId) throws Exception {
		// TODO Auto-generated method stub
		UserOrder order = findOrderById(orderId);
		
		if (userId.equals(order.getUser().getId())) {
			order.setOrderStatus(OrderStatus.CANCELED);
			return orderRepository.save(order);
		}
		throw new Exception("You can't cancel another user order...");
	}

	@Override
	public OrderItem findOrderItemById(Long orderItemId) {
		// TODO Auto-generated method stub
		return orderItemRepository.findById(orderItemId).get();
	}

}
