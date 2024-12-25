package com.xyz.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.xyz.models.Cart;
import com.xyz.models.Coupon;
import com.xyz.models.User;
import com.xyz.repository.CartRepository;
import com.xyz.repository.CouponRepository;
import com.xyz.repository.UserRepository;
import com.xyz.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	

	@Override
	public Cart applyCoupon(String code, double orderValue, User user) throws Exception {
		// TODO Auto-generated method stub
		Coupon coupon = couponRepository.findByCode(code);
		Cart cart = cartRepository.findByUserId(user.getId());
		if (coupon==null) {
			throw new Exception("coupon code not valid...");
		}
		if (user.getUsedCoupon().contains(coupon)) {
			throw new Exception("coupon code already used...");
		}
		if (orderValue<coupon.getMinimumOrderValue()) {
			throw new Exception("your purchase value is less than coupon minimum price value...");
		}
		
		if (coupon.isActive() && LocalDate.now().isAfter(coupon.getValidityStartDate()) && LocalDate.now().isBefore(coupon.getValidityEndDate())) {    
		
			user.getUsedCoupon().add(coupon);
			userRepository.save(user);
			
			double discountedPrice = ( cart.getTotalSellingPrice()*coupon.getDiscountedPercent() )/100;
			cart.setTotalSellingPrice(cart.getTotalSellingPrice()-discountedPrice);
			cart.setCouponCode(code);
			cart = cartRepository.save(cart);
			return cart;
		
		}
		
		return cart;
	}

	@Override
	public Cart removeCoupon(String code, User user) throws Exception {
		// TODO Auto-generated method stub
		Coupon coupon = couponRepository.findByCode(code);
		if (coupon==null) {
			throw new Exception("coupon code not found...");
		}
		Cart cart = cartRepository.findByUserId(user.getId());
		
		user.getUsedCoupon().remove(coupon);
		userRepository.save(user);
		
		double discountedPrice = ( cart.getTotalSellingPrice()*coupon.getDiscountedPercent() )/100;
		cart.setTotalSellingPrice(cart.getTotalSellingPrice()+discountedPrice);
		cart.setCouponCode(null);
		cart = cartRepository.save(cart);
		return cart;
	}

	@Override
	public Coupon findCouponById(Long id) {
		// TODO Auto-generated method stub
		return couponRepository.findById(id).get();
	}

	@Override
	@PreAuthorize("hasRole ('ADMIN')")
	public Coupon createCoupon(Coupon coupon) {
		// TODO Auto-generated method stub
		return couponRepository.save(coupon);
	}

	@Override
	public List<Coupon> getAllCoupon() {
		// TODO Auto-generated method stub
		return couponRepository.findAll();
	}

	@Override
	@PreAuthorize("hasRole ('ADMIN')")
	public void deleteCoupon(Long couponId) {
		// TODO Auto-generated method stub
		couponRepository.deleteById(couponId);
	}

}
