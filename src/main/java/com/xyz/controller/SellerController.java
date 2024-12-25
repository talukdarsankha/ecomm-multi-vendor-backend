package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Exception.SellerException;
import com.xyz.Request.LoginRequest;
import com.xyz.Response.ApiResponse;
import com.xyz.Response.AuthResponse;
import com.xyz.domain.AccountStatus;
import com.xyz.models.Seller;
import com.xyz.models.SellerReport;
import com.xyz.models.User;
import com.xyz.models.VerificationCode;
import com.xyz.repository.SellerRepository;
import com.xyz.repository.VerificationCodeRepository;
import com.xyz.service.AuthService;
import com.xyz.service.SellerReportService;
import com.xyz.service.SellerService;


@RestController
@RequestMapping("/api/seller")
public class SellerController {
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private SellerReportService sellerReportService;
	
	@Autowired
	private VerificationCodeRepository verificationCodeRepository;
	
		
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Seller> getSellerById(@PathVariable("id") Long id) throws SellerException{   
		
		Seller seller = sellerService.findSellerById(id);
		
		return new ResponseEntity<Seller>(seller,HttpStatus.OK);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<Seller> getSellerProfileByJwt(@RequestHeader("Authorization") String jwt) throws SellerException{   
		Seller seller = sellerService.findSellerByJwt(jwt);
		return new ResponseEntity<Seller>(seller,HttpStatus.OK);
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<List<Seller>> getAllSeller(@RequestParam("accountStatus") AccountStatus accountStatus) throws SellerException{   
		List<Seller> sellers = sellerService.getAllSellers(accountStatus);
		return new ResponseEntity<List<Seller>>(sellers,HttpStatus.OK);
	}
	
	
	@PutMapping("/updateAccount")
	public ResponseEntity<Seller> updateAccount(@RequestHeader("Authorization") String jwt) throws SellerException{   
		Seller seller = sellerService.findSellerByJwt(jwt);
		Seller updateSeller = sellerService.updateSeller(seller.getId(), seller);
		return new ResponseEntity<Seller>(updateSeller,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteSeller(@PathVariable("id") Long id) throws SellerException{   
		sellerService.deleteSeller(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Seller Deleted Successfully..."),HttpStatus.OK);
	}
	
	
	
	@GetMapping("/report")
	public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt) throws SellerException{   
		Seller seller = sellerService.findSellerByJwt(jwt);
	    SellerReport sellerReport =	sellerReportService.getSellerReport(seller);
		return new ResponseEntity<SellerReport>(sellerReport,HttpStatus.OK);
	}
	
	
	

}
