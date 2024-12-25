package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.models.Coupon;
import com.xyz.models.Home;
import com.xyz.models.HomeCategory;
import com.xyz.service.CouponService;
import com.xyz.service.HomeCategoryService;
import com.xyz.service.HomeService;

@RestController
@RequestMapping("/api/homeCategory")
public class HomeCategoryController {
	
	@Autowired
	private HomeCategoryService homeCategoryService;
	
	@Autowired
	private HomeService homeService;
	
	
	@PostMapping("/create-homeCategories")
	public ResponseEntity<Home> createCoupon(@RequestBody List<HomeCategory> homeCategories) throws Exception {    
		List<HomeCategory> createdHomeCategories = homeCategoryService.createHomeCategories(homeCategories)	;
	    Home home =	homeService.createHomePageData(createdHomeCategories);
		return new ResponseEntity<Home>(home,HttpStatus.CREATED);
	}
	
	@GetMapping("/admin/homeCategories")
	public ResponseEntity<List<HomeCategory>> getHomeCategory() {    
		List<HomeCategory> allHomeCategories = homeCategoryService.allHomeCategory();
	   return new ResponseEntity<List<HomeCategory>>(allHomeCategories,HttpStatus.OK);
	}
	
	@PutMapping("/admin/update-homeCategory/{categoryId}")
	public ResponseEntity<HomeCategory> updateHomeCategory(@PathVariable Long categoryId,@RequestBody HomeCategory homeCategory) {    
		HomeCategory updatedCategory = homeCategoryService.updateHomeCategory(homeCategory, categoryId);
	   return new ResponseEntity<HomeCategory>(updatedCategory,HttpStatus.OK);
	}
	
	
	

}
