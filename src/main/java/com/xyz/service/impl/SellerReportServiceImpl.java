 package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.config.JwtProvider;
import com.xyz.models.Seller;
import com.xyz.models.SellerReport;
import com.xyz.repository.SellerReportRepository;
import com.xyz.service.SellerReportService;


@Service
public class SellerReportServiceImpl implements SellerReportService {
	
	
	@Autowired
	private SellerReportRepository sellerReportRepository;
	
	
	

	@Override
	public SellerReport getSellerReport(Seller seller) {
		SellerReport sellerReport = sellerReportRepository.findBySellerId(seller.getId());   
		if (sellerReport==null) {
			SellerReport createSellerReport = new SellerReport();
			createSellerReport.setSeller(seller);
			createSellerReport = sellerReportRepository.save(createSellerReport);
			return createSellerReport;
		}
		return sellerReport;
	}

	@Override
	public SellerReport updateSellerReport(SellerReport sellerReport) {
		// TODO Auto-generated method stub
		return sellerReportRepository.save(sellerReport);
	}

}
