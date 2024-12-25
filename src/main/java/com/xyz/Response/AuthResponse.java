package com.xyz.Response;

import com.xyz.domain.USER_ROLE;
import com.xyz.models.BusinessDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
	
	private String jwt;
	
	private String message;
	
	private USER_ROLE role;
	

}
