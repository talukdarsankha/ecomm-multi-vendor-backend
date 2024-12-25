package com.xyz.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyz.domain.USER_ROLE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String locality;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String pinCode;
	
	private String mobile;

}
