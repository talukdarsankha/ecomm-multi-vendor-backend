package com.xyz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.xyz.Response.AuthResponse;
import com.xyz.domain.USER_ROLE;
import com.xyz.models.Seller;
import com.xyz.models.User;
import com.xyz.repository.SellerRepository;
import com.xyz.repository.UserRepository;
import com.xyz.service.AuthService;

@Service
public class CustomUserServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	public static final String sellerPrefix = "seller_";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		if (username.startsWith(sellerPrefix)) {
			String actualUsername = username.substring(sellerPrefix.length());
			
			Seller seller = sellerRepository.findByEmail(actualUsername);
			if (seller!=null) {
				return buildUserDetails(seller.getEmail(),seller.getPassword(), seller.getRole());			
			}
		}else {
			User user = userRepository.findByEmail(username);
			if (user!=null) {
				 return buildUserDetails(user.getEmail(),user.getPassword(), user.getRole());
			}
		}
		
		throw new UsernameNotFoundException("User or Seller not found with this email :"+username);
	}
	
	public UserDetails buildUserDetails(String email,String password, USER_ROLE role) {
		if (role==null) {
			role = USER_ROLE.ROLE_CUSTOMER;       
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		
		
		return new org.springframework.security.core.userdetails.User(email,password,authorities);
	}

}
