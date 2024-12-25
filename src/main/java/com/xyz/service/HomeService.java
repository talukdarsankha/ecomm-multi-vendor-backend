package com.xyz.service;

import java.util.List;

import com.xyz.models.Home;
import com.xyz.models.HomeCategory;

public interface HomeService {
	
	public Home createHomePageData(List<HomeCategory> allCategories);

}
