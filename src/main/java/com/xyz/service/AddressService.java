package com.xyz.service;


import com.xyz.models.Address;
import com.xyz.models.User;

public interface AddressService {
	
	
	public Address addAddressOfUser(Address address, User user);

}
