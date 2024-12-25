package com.xyz.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyz.domain.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonIgnore
	@ManyToOne 
	private UserOrder order;
	
	@ManyToOne
	private Product product;
	
	private String size;
	
	private Integer quantity;
	
	private Integer mrpPrice;
	
	private Integer sellingPrice;
	
	private Long userId;
	

}
