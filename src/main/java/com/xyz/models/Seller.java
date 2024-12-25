package com.xyz.models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyz.domain.AccountStatus;
import com.xyz.domain.USER_ROLE;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Seller {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String sellerName;
	
	private String mobile;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // for when we fetch data then this parameter not come from database only we can only write this parameter in database
	private String password;
	
	@Embedded
	private BusinessDetails  businessDetails;
	
	@Embedded
	private BankDetails  bankDetails;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address pickUpAddress = new Address();
	
	private String gstin;
	
	private USER_ROLE role = USER_ROLE.ROLE_SELLER;
	

	private boolean isEmailVerified=false;
	
	private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;
	
	
	
	
	

}
