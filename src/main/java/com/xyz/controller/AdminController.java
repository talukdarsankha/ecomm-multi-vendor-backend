package com.xyz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.domain.AccountStatus;
import com.xyz.models.Cart;
import com.xyz.models.Seller;
import com.xyz.models.User;
import com.xyz.service.ProductService;
import com.xyz.service.SellerService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	
	@Autowired
	private SellerService sellerService;
	
	
	
	
	@PutMapping("/seller/{sellerId}/status/{status}")
	public ResponseEntity<Seller> getUserCart(@PathVariable("sellerId") Long sellerId, @PathVariable("status") AccountStatus status) throws Exception{
		
		Seller seller = sellerService.updateSellerAccountStatus(sellerId, status);
		return new ResponseEntity<Seller>(seller, HttpStatus.OK);
	}
	
	

}
