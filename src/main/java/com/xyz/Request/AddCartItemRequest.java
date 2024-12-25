package com.xyz.Request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCartItemRequest {
	
	 private String size;
		
	 private int quantity;
	 
	 private Long productId;

}
