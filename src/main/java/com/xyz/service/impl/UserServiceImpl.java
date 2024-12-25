package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.config.JwtProvider;
import com.xyz.models.User;
import com.xyz.repository.UserRepository;
import com.xyz.service.AuthService;
import com.xyz.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserRepository userRepository;	
	

	@Override
	public User findUserByJwt(String jwt) throws Exception {
		 String email = jwtProvider.getEmailFromToken(jwt);
		 User user = findUserByEmail(email);
		 
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		
		User user = userRepository.findByEmail(email);
		 
		 if (user==null) {
			throw new Exception("User Not Found With This Email : "+email);
		}
		 return user;
	}

	@Override
	public User findUserById(Long Id) {
		User user = userRepository.findById(Id).get();
		return user;
	}

}
