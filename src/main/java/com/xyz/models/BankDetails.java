package com.xyz.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {
	
	private String accountNumber;
	private String ifscCode;
	private String accountHolderName;
	
	

}
