package com.xyz.service;

import com.xyz.Request.LoginRequest;
import com.xyz.Request.SignUpRequest;
import com.xyz.Response.AuthResponse;
import com.xyz.Response.OtpResponse;
import com.xyz.domain.USER_ROLE;
import com.xyz.models.Seller;

public interface AuthService {
	
	public OtpResponse sentOtp(String email, USER_ROLE role) throws Exception;
	
	public String createUser(SignUpRequest request) throws Exception;
	
	public AuthResponse signin(LoginRequest loginRequest);
	
    public Seller createSeller(Seller seller) throws Exception;
	
	public Seller verifySellerEmail(String email) throws Exception;

}
