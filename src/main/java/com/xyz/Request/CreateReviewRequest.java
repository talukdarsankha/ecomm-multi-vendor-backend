package com.xyz.Request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequest {
	
	private String reviewText;
	private double rating;
	private List<String> productimages;
	

}
