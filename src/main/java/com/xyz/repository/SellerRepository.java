package com.xyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.domain.AccountStatus;
import com.xyz.domain.USER_ROLE;
import com.xyz.models.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
	
	public Seller findByEmail(String email);
	
	public List<Seller> findByAccountStatus(AccountStatus status);
	

}
