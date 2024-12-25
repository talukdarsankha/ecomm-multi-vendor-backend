package com.xyz.models;

import java.util.HashSet;
import java.util.Set;

import com.xyz.domain.PaymentMethod;
import com.xyz.domain.PaymentOrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long amount;
	
	private PaymentOrderStatus paymentOrderStatus = PaymentOrderStatus.PENDING;
	
	private PaymentMethod paymentMethod;
	
	@ManyToOne
	private User user;
	
	@OneToMany
	private Set<UserOrder> orders = new HashSet<>();
	
	

}
