package com.xyz.service.impl;

import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.xyz.domain.PaymentOrderStatus;
import com.xyz.domain.PaymentStatus;
import com.xyz.models.PaymentOrder;
import com.xyz.models.User;
import com.xyz.models.UserOrder;
import com.xyz.repository.OrderRepository;
import com.xyz.repository.PaymentOrderRepository;
import com.xyz.service.OrderService;
import com.xyz.service.PaymentService;


@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentOrderRepository paymentOrderRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	private String apiKey ="rzp_test_AeBtpPsFloC1I4" ; 
	
	private String apiSecret ="RdvTnkTGa2G7lLyMN1QoXKSZ" ;
	
	private String stripeApiSecret ="sk_test_51QK1tNBdQIRG6wxLfGSTJliNRklTx0zrac07oRi4X8SHslE4wn85ylTicX4XM1OHwSQtiTMk9amPXgNNctd1Vf5600k5koHu6e" ;
	
	private String frontend_BASE_URL = "https://ecommerce-multi-vendor-frontend.vercel.app";
	

	@Override
	public PaymentOrder createPaymentOrder(User user, Set<UserOrder> orders) {
		// TODO Auto-generated method stub
		
		 long amount = orders.stream().mapToLong(order -> {
			  return order.getTotalSellingPrice();
                 }).sum();
		 
		 amount = amount+40;  // this 40 rupees for delivery Price for every purchase as define in frontend

        System.out.println("Calculated amount: " + amount);
        
		PaymentOrder paymentOrder = new PaymentOrder();
		paymentOrder.setAmount(amount);
		paymentOrder.setUser(user);

		paymentOrder.setOrders(orders);
		
		return paymentOrderRepository.save(paymentOrder);
	}

	@Override
	public PaymentOrder getPaymentOrderById(Long paymentId) throws Exception {
		// TODO Auto-generated method stub
		return paymentOrderRepository.findById(paymentId).orElseThrow(()->new Exception("PaymentOrder not found with this id :"+paymentId));  
	}

	
	
	
	@Override
	public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String razorpayPaymentId) throws RazorpayException {
		// TODO Auto-generated method stub
		
		if (paymentOrder.getPaymentOrderStatus().equals(PaymentOrderStatus.PENDING)) {
			RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);
			Payment payment = razorpayClient.payments.fetch(razorpayPaymentId);
			String status = payment.get("status");
			
			if (status.equals("captured")) {
				Set<UserOrder> orders = paymentOrder.getOrders();
				for(UserOrder order: orders) {
					order.getPaymentDetails().setPaymentStatus(PaymentStatus.COMPLETED);
					orderRepository.save(order);
				}
				paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
				paymentOrderRepository.save(paymentOrder);
				return true;
			}
			
			paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.FAILURE);
			paymentOrderRepository.save(paymentOrder);
			return false;
		}
		return false;
	} 

	@Override
	public PaymentLink createRazorpayPaymentLink(User user, PaymentOrder paymentOrder) throws RazorpayException {
		// TODO Auto-generated method stub
	
		long amount = paymentOrder.getAmount()*100;
		
		try {
			RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);
			
			JSONObject paymentLinkRequest = new JSONObject();
			paymentLinkRequest.put("amount", amount);
			
			System.out.println("paymentOrder amout :"+paymentOrder.getAmount());   
			
			paymentLinkRequest.put("currency", "INR");
			
			JSONObject customer = new JSONObject();
			customer.put("name", user.getFullname());
			customer.put("email", user.getEmail());
			paymentLinkRequest.put("customer", customer);
			
			JSONObject notify = new JSONObject();
			notify.put("email", true);
			paymentLinkRequest.put("notify", notify);
			
			paymentLinkRequest.put("callback_url", frontend_BASE_URL+"/payment-success/"+paymentOrder.getId());
			paymentLinkRequest.put("callback_method", "get");
			
			PaymentLink paymentLink =  razorpayClient.paymentLink.create(paymentLinkRequest);
			
			String paymentLinkUrl = paymentLink.get("short_url");
			String paymentLinkid = paymentLink.get("id");
			
			return paymentLink;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RazorpayException(e.getMessage());
		}
		
	}
	
	

	@Override
	public String createStripePaymentLink(User user, PaymentOrder paymentOrder) throws StripeException {
	    Stripe.apiKey = stripeApiSecret;

	    // Assuming the amount in `paymentOrder` is in INR
	    long amountInINR = paymentOrder.getAmount();
	    
	    // Convert INR to USD (assuming a static conversion rate for simplicity, e.g., 1 USD = 83.00 INR)
	    double conversionRate = 83.00; // Use a dynamic API for accurate rates in production
	    long amountInUSD = Math.round(amountInINR / conversionRate * 100); // Convert to cents (Stripe requires amounts in smallest currency unit)

	    SessionCreateParams params = SessionCreateParams.builder()
	            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
	            .setMode(SessionCreateParams.Mode.PAYMENT)
	            .setSuccessUrl(frontend_BASE_URL+"/payment-success/" + paymentOrder.getId())
	            .setCancelUrl("http://localhost:3000/payment-cancel")
	            .addLineItem(SessionCreateParams.LineItem.builder()
	                    .setQuantity(1L)
	                    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
	                            .setCurrency("usd") // Use USD since we're converting to USD
	                            .setUnitAmount(amountInUSD) // Set converted amount in USD cents
	                            .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
	                                    .setName("ecommerce store")
	                                    .build())
	                            .build())
	                    .build())
	            .build();

	    Session session = Session.create(params);
	    return session.getUrl();
	}


	

}
