package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Exception.SellerException;
import com.xyz.domain.OrderStatus;
import com.xyz.models.Seller;
import com.xyz.models.UserOrder;
import com.xyz.repository.SellerRepository;
import com.xyz.service.OrderService;
import com.xyz.service.SellerService;

@RestController
@RequestMapping("/api/seller/order")
public class SellerOrderController {
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private OrderService orderService;
	
	
	
	
	@GetMapping("/")
	public ResponseEntity<List<UserOrder>> getSellerAllOrders(@RequestHeader("Authorization") String jwt) throws Exception{
		Seller seller = sellerService.findSellerByJwt(jwt);
		List<UserOrder> allOrders = orderService.gertAllOrdersBySeller(seller.getId());
		return new ResponseEntity<List<UserOrder>>(allOrders,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/status/{orderStatus}")
	public ResponseEntity<UserOrder> updateOrderStatus(@PathVariable("orderId") Long orderId, @PathVariable("orderStatus") OrderStatus orderStatus ) throws SellerException{
		UserOrder order = orderService.updateOrderStatus(orderId,orderStatus);
		return new ResponseEntity<UserOrder>(order,HttpStatus.OK);
	}
	
	

}
