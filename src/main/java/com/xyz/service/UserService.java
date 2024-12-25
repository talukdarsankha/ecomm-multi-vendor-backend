package com.xyz.service;

import com.xyz.models.User;

public interface UserService {
	
	public User findUserByJwt(String jwt) throws Exception;
	
	public User findUserByEmail(String email) throws Exception;
	
	public User findUserById(Long Id);

}
