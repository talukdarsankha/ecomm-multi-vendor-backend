package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.models.VerificationCode;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

	public VerificationCode findByEmail(String email);
	
	public VerificationCode findByOtp(String otp);
	
}
