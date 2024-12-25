package com.xyz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Entity
public class SellerReport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private Seller seller;
	
	private Long totalEarnings = 0L;
	
	private Long totalSales = 0L;
	
	
	private Long totalRefunds = 0L;
	
	private Long totalTax = 0L;
	
	private Long netEarnings = 0L;
	
	private Long totalOrders = 0L;
	
	private Long totalCanceledOrders = 0L;
	
	private Long totalTransactions = 0L;
	
	
	

}
