package com.xyz.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.PaymentLink;
import com.xyz.Response.PaymentLinkResponse;
import com.xyz.domain.PaymentMethod;
import com.xyz.models.Address;
import com.xyz.models.Cart;
import com.xyz.models.CartItem;
import com.xyz.models.OrderItem;
import com.xyz.models.PaymentOrder;
import com.xyz.models.Seller;
import com.xyz.models.SellerReport;
import com.xyz.models.User;
import com.xyz.models.UserOrder;
import com.xyz.repository.PaymentOrderRepository;
import com.xyz.service.CartService;
import com.xyz.service.OrderService;
import com.xyz.service.PaymentService;
import com.xyz.service.ProductService;
import com.xyz.service.SellerReportService;
import com.xyz.service.SellerService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private SellerReportService sellerReportService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private PaymentOrderRepository paymentOrderRepository;
	
	
	
	@PostMapping("/create")
	public ResponseEntity<PaymentLinkResponse> createOrder(@RequestHeader("Authorization") String jwt, @RequestParam("addressId") Long addressId,@RequestParam("paymentMethod") PaymentMethod paymentMethod) throws Exception{
		
		User user = userService.findUserByJwt(jwt);
		Cart userCart = cartService.findUserCart(user);

		Set<UserOrder> orders = orderService.createOrder(user, addressId, userCart);
		 PaymentOrder paymentOrder =  paymentService.createPaymentOrder(user, orders);
		 
		PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
		
		if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
		 PaymentLink paymentLink =	paymentService.createRazorpayPaymentLink(user, paymentOrder);
		
		 String paymentLinkUrl = paymentLink.get("short_url");
		 String paymentLinkId = paymentLink.get("id");
		 
		 paymentLinkResponse.setPaymentLinkUrl(paymentLinkUrl);
		 
		 paymentOrder.setPaymentMethod(PaymentMethod.RAZORPAY);
		 paymentOrderRepository.save(paymentOrder);
		}else {
			String paymentUrl = paymentService.createStripePaymentLink(user,paymentOrder);
			 
			paymentOrder.setPaymentMethod(PaymentMethod.STRIPE);
			paymentOrderRepository.save(paymentOrder);
			
			paymentLinkResponse.setPaymentLinkUrl(paymentUrl);
		}
				
		return new ResponseEntity<PaymentLinkResponse>(paymentLinkResponse,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/user")
	public ResponseEntity<List<UserOrder>> userAllOrders(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwt(jwt);
		List<UserOrder> allOrders = orderService.userAllOrders(user.getId());
		return new ResponseEntity<List<UserOrder>>(allOrders,HttpStatus.OK);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<UserOrder> findOrderById(@PathVariable Long orderId) throws Exception{
		UserOrder order = orderService.findOrderById(orderId);
		
		return new ResponseEntity<UserOrder>(order,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/orderItem/{orderItemId}")
	public ResponseEntity<OrderItem> findOrderItemById(@PathVariable Long orderItemId) throws Exception{
		OrderItem orderItem = orderService.findOrderItemById(orderItemId);
		
		return new ResponseEntity<OrderItem>(orderItem,HttpStatus.ACCEPTED);
	}
	 
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<UserOrder> cancleOrder(@RequestHeader("Authorization") String jwt, @PathVariable Long orderId) throws Exception{
		User user = userService.findUserByJwt(jwt);
		
		UserOrder order = orderService.cancelOrder(orderId, user.getId());
		
		Seller seller = sellerService.findSellerById(order.getSellerId());
		SellerReport sellerReport = sellerReportService.getSellerReport(seller);
		sellerReport.setTotalCanceledOrders(sellerReport.getTotalCanceledOrders()+1);
		
		sellerReportService.updateSellerReport(sellerReport);
		
		return new ResponseEntity<UserOrder>(order,HttpStatus.ACCEPTED);
	}
	



}
