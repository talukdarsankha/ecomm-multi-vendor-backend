package com.xyz.service;

import java.util.List;

import com.xyz.models.Cart;
import com.xyz.models.Coupon;
import com.xyz.models.User;

public interface CouponService {
	
	public Cart applyCoupon(String code, double orderValue, User user) throws Exception;
	
	public Cart removeCoupon(String code, User user) throws Exception;
	
	public Coupon findCouponById(Long id);
	
	public Coupon createCoupon(Coupon coupon);
	
	public List<Coupon> getAllCoupon();
	
	public void deleteCoupon(Long couponId);
	
	

}
