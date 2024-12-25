package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.models.SellerReport;

@Repository
public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {

	public SellerReport findBySellerId(Long sellerId);
}
