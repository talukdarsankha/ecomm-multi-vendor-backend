package com.xyz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Request.LoginRequest;
import com.xyz.Request.OtpRequset;
import com.xyz.Request.SignUpRequest;
import com.xyz.Response.ApiResponse;
import com.xyz.Response.AuthResponse;
import com.xyz.Response.OtpResponse;
import com.xyz.domain.USER_ROLE;
import com.xyz.models.Seller;
import com.xyz.models.User;
import com.xyz.models.VerificationCode;
import com.xyz.repository.UserRepository;
import com.xyz.repository.VerificationCodeRepository;
import com.xyz.service.AuthService;
import com.xyz.service.SellerService;
import com.xyz.service.impl.EmailServiceImpl;
import com.xyz.utill.OtpUtill;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private VerificationCodeRepository verificationCodeRepository;
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	
	
	
	
	@PostMapping("/sent/otp")
	public ResponseEntity<OtpResponse> setOtp(@RequestBody OtpRequset requset) throws Exception{   
		OtpResponse otpResponse = authService.sentOtp(requset.getEmail(),requset.getRole());
		
		return new ResponseEntity<OtpResponse>(otpResponse,HttpStatus.OK);
	}
	
	
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signUpUser(@RequestBody SignUpRequest signUpRequest) throws Exception{   
		String jwt = authService.createUser(signUpRequest);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("User Register Successfully...");
		authResponse.setRole(USER_ROLE.ROLE_CUSTOMER);
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	}
	
	
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signinUser(@RequestBody LoginRequest loginRequest) throws Exception{   
		AuthResponse authResponse = authService.signin(loginRequest);
			
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
	}
	
	
	@PostMapping("/signup-seller")
	public ResponseEntity<Seller> signUpSeller(@RequestBody Seller seller) throws Exception{   
		
		Seller createSeller = authService.createSeller(seller);
		
		String otp = OtpUtill.generateOtp();
		
		VerificationCode verificationCode = new VerificationCode();
		verificationCode.setOtp(otp);
		verificationCode.setEmail(createSeller.getEmail());
		verificationCodeRepository.save(verificationCode);
		
		String subject = "Account Activation !!!";
		String text = "Activate your ecommerce store account using this link :";
		String url = "http://localhost:3000/verify-seller/";
		text =text+url;
		
		emailServiceImpl.sendVerificationOtp(createSeller.getEmail(), subject, text);
		
		
		return new ResponseEntity<Seller>(createSeller,HttpStatus.CREATED);
	}
	
	
	@PostMapping("/signin-seller")
	public ResponseEntity<AuthResponse> signinSeller(@RequestBody LoginRequest loginRequest){   
		
		
		loginRequest.setEmail("seller_"+loginRequest.getEmail());
//		System.out.print(loginRequest);
		AuthResponse authResponse = authService.signin(loginRequest);
		
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

	}
	
	
	@PutMapping("/verify/{otp}")
	public ResponseEntity<Seller> verifySeller(@PathVariable("otp") String otp) throws Exception{   
		
		VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);
		if (verificationCode == null) {
			throw new Exception("Wrong Otp...");
		}
		
		Seller seller = authService.verifySellerEmail(verificationCode.getEmail());
		return new ResponseEntity<Seller>(seller,HttpStatus.OK);
	}
	
	
	


}
