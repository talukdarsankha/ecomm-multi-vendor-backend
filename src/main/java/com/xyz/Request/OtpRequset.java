package com.xyz.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyz.domain.AccountStatus;
import com.xyz.domain.USER_ROLE;
import com.xyz.models.Address;
import com.xyz.models.BankDetails;
import com.xyz.models.BusinessDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpRequset {
	
	private String email;
	private USER_ROLE role;

}
