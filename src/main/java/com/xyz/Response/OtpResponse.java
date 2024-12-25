package com.xyz.Response;

import com.xyz.domain.USER_ROLE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpResponse {
    
	private String message;
	
	private String otp;
	
	private String status;
	
}

