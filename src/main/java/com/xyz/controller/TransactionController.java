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
import com.xyz.models.Transaction;
import com.xyz.models.UserOrder;
import com.xyz.service.AuthService;
import com.xyz.service.SellerService;
import com.xyz.service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private TransactionService transactionService;
	
	
	
	
	@GetMapping("/")
	public ResponseEntity<List<Transaction>> getAllTransactions(){    
		
		return new ResponseEntity<List<Transaction>>(transactionService.getAllTransaction(),HttpStatus.OK);
	}
	
	
	@GetMapping("/seller/")
	public ResponseEntity<List<Transaction>> getAllTransactionBySeller(@RequestHeader("Authorization") String jwt) throws SellerException{    
		Seller seller = sellerService.findSellerByJwt(jwt);
		List<Transaction> transactions = transactionService.getTransactionBySeller(seller);
		return new ResponseEntity<List<Transaction>>(transactions,HttpStatus.OK);
	}
	
	
	
	
	

}
