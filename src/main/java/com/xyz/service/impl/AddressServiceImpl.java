package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.models.Address;
import com.xyz.models.User;
import com.xyz.repository.AddressRepository;
import com.xyz.repository.UserRepository;
import com.xyz.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public Address addAddressOfUser(Address address,User user) {
		// TODO Auto-generated method stub
		if (address!=null && !user.getAddress().contains(address)) {
			address = addressRepository.save(address);
			user.getAddress().add(address);
			userRepository.save(user);
		}
		return address;
	}

}
