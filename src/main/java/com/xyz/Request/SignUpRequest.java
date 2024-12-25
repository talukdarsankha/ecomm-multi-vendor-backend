package com.xyz.Request;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyz.domain.USER_ROLE;
import com.xyz.models.Address;
import com.xyz.models.Coupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
	
	private String email;
	
	private String otp;
	
	private String fullname;
	
}
