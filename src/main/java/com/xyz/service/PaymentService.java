package com.xyz.service;

import java.util.Set;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.xyz.models.PaymentOrder;
import com.xyz.models.User;
import com.xyz.models.UserOrder;

public interface PaymentService {
	
	public PaymentOrder createPaymentOrder(User user, Set<UserOrder> orders);
	
	public PaymentOrder getPaymentOrderById(Long paymentId) throws Exception;
		
	public Boolean proceedPaymentOrder(PaymentOrder paymentOrder , String paymentLinkId) throws RazorpayException;   

	public PaymentLink createRazorpayPaymentLink(User user, PaymentOrder paymentOrder) throws RazorpayException;
	
	public String createStripePaymentLink(User user,  PaymentOrder paymentOrder) throws StripeException;
}
