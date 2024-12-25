package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.models.Coupon;



@Repository
public interface CouponRepository  extends JpaRepository<Coupon, Long>{
	
	public Coupon findByCode(String code);

}
   