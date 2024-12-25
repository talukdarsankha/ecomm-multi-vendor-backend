package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Request.OtpRequset;
import com.xyz.Response.ApiResponse;
import com.xyz.models.Cart;
import com.xyz.models.Coupon;
import com.xyz.models.User;
import com.xyz.service.AuthService;
import com.xyz.service.CouponService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private UserService userService;
	
	
	
	@PostMapping("/apply-remove")
	public ResponseEntity<Cart> applyRemoveCoupon(@RequestHeader("Authorization") String jwt, @RequestParam String apply, @RequestParam String code, @RequestParam double orderValue) throws Exception {    
		User user = userService.findUserByJwt(jwt);
		Cart cart;
		
		if (apply.equals("true")) {
			cart = couponService.applyCoupon(code, orderValue, user);
		}else {
			cart = couponService.removeCoupon(code, user);
		}
		
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PostMapping("/admin/create")
	public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) throws Exception {    
		Coupon createdCoupon = couponService.createCoupon(coupon);		
		return new ResponseEntity<Coupon>(createdCoupon,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/admin/{couponId}")
	public ResponseEntity<ApiResponse> deleteCoupon(@PathVariable Long couponId) throws Exception {    
		couponService.deleteCoupon(couponId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("coupon deleted successfully..."),HttpStatus.OK);
	}

	
	@GetMapping("/")
	public ResponseEntity<List<Coupon>> allCoupon()  {    
		List<Coupon> coupons = couponService.getAllCoupon();	
		return new ResponseEntity<List<Coupon>>(coupons,HttpStatus.OK);
	}
	
}
