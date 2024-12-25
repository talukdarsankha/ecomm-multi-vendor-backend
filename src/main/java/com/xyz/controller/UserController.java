package com.xyz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.config.JwtProvider;
import com.xyz.models.Address;
import com.xyz.models.User;
import com.xyz.repository.UserRepository;
import com.xyz.service.AddressService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserFromJwt(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwt(jwt);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@PostMapping("/add-address")
	public ResponseEntity<Address> addAddresst(@RequestHeader("Authorization") String jwt,@RequestBody Address address) throws Exception{
		User user = userService.findUserByJwt(jwt);
		Address createdAddress = addressService.addAddressOfUser(address, user);
		return new ResponseEntity<Address>(createdAddress,HttpStatus.CREATED);
	}

}
