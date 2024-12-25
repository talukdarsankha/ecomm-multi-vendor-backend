package com.xyz.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Response.ApiResponse;
import com.xyz.Response.PaymentLinkResponse;
import com.xyz.domain.PaymentMethod;
import com.xyz.models.Address;
import com.xyz.models.Cart;
import com.xyz.models.PaymentOrder;
import com.xyz.models.Seller;
import com.xyz.models.SellerReport;
import com.xyz.models.User;
import com.xyz.models.UserOrder;
import com.xyz.service.CartService;
import com.xyz.service.OrderService;
import com.xyz.service.PaymentService;
import com.xyz.service.SellerReportService;
import com.xyz.service.SellerService;
import com.xyz.service.TransactionService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SellerReportService sellerReportService;
	
	@Autowired
	private TransactionService transactionService;
	
		
	
	@GetMapping("/{paymentOrderId}")
	public ResponseEntity<ApiResponse> proceedPaymentSuccessOrFailure(@RequestHeader("Authorization") String jwt, @PathVariable("paymentOrderId") Long paymentOrderId, @RequestParam("razorpayPaymentId") String razorpayPaymentId ) throws Exception{
		User user = userService.findUserByJwt(jwt);
			
		PaymentOrder paymentOrder = paymentService.getPaymentOrderById(paymentOrderId);

		boolean paymentSuccess = paymentService.proceedPaymentOrder(paymentOrder, razorpayPaymentId);
		
		if (paymentSuccess) {
			for(UserOrder order: paymentOrder.getOrders()) {
				
				transactionService.createTransaction(order);
				
			  Seller seller = sellerService.findSellerById(order.getSellerId());
			  
			  SellerReport sellerReport = sellerReportService.getSellerReport(seller);
			  sellerReport.setTotalOrders(sellerReport.getTotalOrders()+1);
			  sellerReport.setTotalEarnings(sellerReport.getTotalEarnings()+order.getTotalSellingPrice());
			  sellerReport.setTotalSales(sellerReport.getTotalSales()+order.getOrderItems().size());
			  sellerReportService.updateSellerReport(sellerReport);
			}
			
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setMessage("Payyyyyment Successfull...");
			
			return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
		}	
		
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Payyyyyment Faliure ...");
	
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	
//		return new ResponseEntity<PaymentOrder>(paymentOrder,HttpStatus.OK);
		
	}

}
