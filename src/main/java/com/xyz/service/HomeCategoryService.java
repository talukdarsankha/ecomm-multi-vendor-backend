package com.xyz.service;

import java.util.List;

import com.xyz.models.HomeCategory;

public interface HomeCategoryService {
	
	public HomeCategory createHomeCategory(HomeCategory homeCategory);
	
	public List<HomeCategory> createHomeCategories(List<HomeCategory> homeCategories);
	
	public HomeCategory updateHomeCategory(HomeCategory homeCategory, Long homeCategoryId); 
	
	public List<HomeCategory> allHomeCategory(); 
	
}
