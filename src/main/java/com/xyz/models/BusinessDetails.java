package com.xyz.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyz.domain.AccountStatus;
import com.xyz.domain.USER_ROLE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDetails {
	
	private String businessName;
	private String businessEmail;
	private String businessMobile;
	private String businessAddress;
	private String logo;
	private String banner;	
	

}
