package com.xyz.Request;

import java.time.LocalDateTime;
import java.util.List;

import com.xyz.models.Category;
import com.xyz.models.Product;
import com.xyz.models.Review;
import com.xyz.models.Seller;

import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
	
    private String title;
	
	private String description;
	
	private int mrpPrice;
	
	private int sellingPrice;
	
	private int quantity;
	
	private int discountedPercent;
	
	private String color;
	
	@ElementCollection
	private List<String> images;
	
	private String sizes;
	
	private String category1;
	
	private String category2;
	
	private String category3;

}
