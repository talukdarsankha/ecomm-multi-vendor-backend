package com.xyz.models;

import java.util.Set;

import com.xyz.domain.HomeCategorySection;
import com.xyz.domain.PaymentMethod;
import com.xyz.domain.PaymentOrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HomeCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String image;
	
	private String categoryId;
	
	private HomeCategorySection homeCategorySection;

}
