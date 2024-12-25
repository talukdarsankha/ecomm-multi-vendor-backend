package com.xyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	public List<Transaction> findBySellerId(Long sellerId);
	
}
