package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Response.ApiResponse;
import com.xyz.models.Coupon;
import com.xyz.models.Deal;
import com.xyz.service.CouponService;
import com.xyz.service.DealService;

@RestController
@RequestMapping("/api/admin/deal")
public class DealController {
	
	@Autowired
	private DealService dealService;
	
	@PostMapping("/create")
	public ResponseEntity<Deal> createDeal(@RequestBody Deal deal) {    
		Deal createdDeal = dealService.createDeal(deal);		
		return new ResponseEntity<Deal>(createdDeal,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Deal> updateDeal(@PathVariable Long id, Deal deal) throws Exception {    
		Deal updatedDeal = dealService.updateDeal(deal, id);
		return new ResponseEntity<Deal>(updatedDeal,HttpStatus.OK);
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteDeal(@PathVariable Long id) {    
		 dealService.deleteDeal(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Deal Deleted Successfully..."),HttpStatus.OK);
	}

	
	@GetMapping("/")
	public ResponseEntity<List<Deal>> allDeals(@PathVariable Long id, Deal deal) {    
		List<Deal> deals = dealService.getAllDeals();
		return new ResponseEntity<List<Deal>>(deals,HttpStatus.OK);
	}

}
