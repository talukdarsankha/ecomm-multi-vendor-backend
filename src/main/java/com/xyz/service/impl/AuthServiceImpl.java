package com.xyz.service.impl;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xyz.Request.LoginRequest;
import com.xyz.Request.SignUpRequest;
import com.xyz.Response.AuthResponse;
import com.xyz.Response.OtpResponse;
import com.xyz.config.JwtProvider;
import com.xyz.domain.USER_ROLE;
import com.xyz.models.Address;
import com.xyz.models.Cart;
import com.xyz.models.Seller;
import com.xyz.models.User;
import com.xyz.models.VerificationCode;
import com.xyz.repository.AddressRepository;
import com.xyz.repository.CartRepository;
import com.xyz.repository.SellerRepository;
import com.xyz.repository.UserRepository;
import com.xyz.repository.VerificationCodeRepository;
import com.xyz.service.AuthService;
import com.xyz.service.SellerService;
import com.xyz.utill.OtpUtill;


@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private VerificationCodeRepository verificationCodeRepository;
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private CustomUserServiceImpl customUserServiceImpl;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private AddressRepository addressRepository;
	
	
	
	
	@Override
	public OtpResponse sentOtp(String email,USER_ROLE role) throws Exception {
		// TODO Auto-generated method stub
		 
		String signinPrefix = "signin_";   // using this prefix it will work for both signin and signup  and is it for signin then check first is user present or not    
		
		if (email.startsWith(signinPrefix)) {
			email = email.substring(signinPrefix.length());
			
			if (role==USER_ROLE.ROLE_CUSTOMER) {
				
				User user = userRepository.findByEmail(email);
				if (user==null) {
					OtpResponse otpResponse = new OtpResponse();
					otpResponse.setMessage("User account not present with this email !!!");
					otpResponse.setStatus("error");
                      return otpResponse;
				 }
			}else {
				Seller seller = sellerRepository.findByEmail(email);
				if (seller==null) {
					OtpResponse otpResponse = new OtpResponse();
					otpResponse.setMessage("Seller account not present with this email !!!");
					otpResponse.setStatus("error");
                      return otpResponse;				}
			}
			
		}
		
		VerificationCode existVerificationCode = verificationCodeRepository.findByEmail(email);
		if (existVerificationCode!=null) {
			verificationCodeRepository.delete(existVerificationCode);
		}
		
		String otp = OtpUtill.generateOtp();
		VerificationCode verificationCode = new VerificationCode();
		verificationCode.setOtp(otp);
		verificationCode.setEmail(email);
		verificationCodeRepository.save(verificationCode);
		
		String subject = "Ecommerce store login/signup otp";
		String text = "You store login/signup otp is : "+otp;
		
		OtpResponse otpResponse = emailServiceImpl.sendVerificationOtp(email, subject, text);
		if (otpResponse.getStatus()=="success") {
			otpResponse.setOtp(otp);
		}
		
		return otpResponse;
		
	}

	
	

	@Override
	public String createUser(SignUpRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		VerificationCode verificationCode = verificationCodeRepository.findByEmail(request.getEmail());
		if (verificationCode==null || !verificationCode.getOtp().equals(request.getOtp())) {
			throw new Exception("Otp did't match...");
		}
		
		User user = userRepository.findByEmail(request.getEmail());
		if (user==null){
			User createdUser = new User();
			createdUser.setEmail(request.getEmail());
			createdUser.setFullname(request.getFullname());
			createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
			
			createdUser.setPassword(passwordEncoder.encode(request.getOtp()));
			
			 user = userRepository.save(createdUser);
			
			Cart cart = new Cart();
			cart.setUser(createdUser);
			cartRepository.save(cart);
						
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));
		
		Authentication   authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);   
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return jwtProvider.generateToken(authentication);
		
		
		
	}
	
	
	
	
	@Override
	public Seller createSeller(Seller seller) throws Exception {
		// TODO Auto-generated method stub
		Seller existSeller =  sellerRepository.findByEmail(seller.getEmail());
		if (existSeller!=null) {
			throw new Exception("Seller Already exist With This Email : "+seller.getEmail()); 
		}
		Address address = addressRepository.save(seller.getPickUpAddress());
		
		Seller createSeller = new Seller();
		createSeller.setEmail(seller.getEmail());
		
		createSeller.setPassword(passwordEncoder.encode(seller.getEmail()));
		
		createSeller.setSellerName(seller.getSellerName());
		createSeller.setPickUpAddress(address);
		createSeller.setGstin(seller.getGstin());
		createSeller.setRole(USER_ROLE.ROLE_SELLER);
		createSeller.setMobile(seller.getMobile());
		createSeller.setBusinessDetails(seller.getBusinessDetails());
		createSeller.setBankDetails(seller.getBankDetails());
		
		return sellerRepository.save(createSeller);
	}
	
	
	@Override
	public Seller verifySellerEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		Seller existSeller = sellerService.findSellerByEmail(email);
		existSeller.setEmailVerified(true);
		return sellerRepository.save(existSeller);
	}

	
	
	
	
	
	@Override
	public AuthResponse signin(LoginRequest request) {
		String email = request.getEmail();
		String otp = request.getOtp();
		

		
		Authentication authentication = authenticate(email, otp);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Login Successfully...");
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
		
		authResponse.setRole(USER_ROLE.valueOf(role));
		return authResponse;
	 
		
	}
	
	public Authentication authenticate(String email, String otp) {
		UserDetails userDetails = customUserServiceImpl.loadUserByUsername(email);
		
		if (userDetails==null) {
			throw new BadCredentialsException("Invalid Email....");
		}
		
		String sellerPrefix = "seller_" ;
				
		if (email.startsWith(sellerPrefix)) {
		email = email.substring(sellerPrefix.length());
		}
		
		
		
		VerificationCode verificationCode = verificationCodeRepository.findByEmail(email);
		if (verificationCode==null || !verificationCode.getOtp().equals(otp)) {
			throw new BadCredentialsException("Wrong Otp...");
		}
		verificationCodeRepository.delete(verificationCode);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	
	}
	
	

	
}
