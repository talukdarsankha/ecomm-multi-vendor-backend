package com.xyz.service;

import com.xyz.models.Seller;
import com.xyz.models.SellerReport;

public interface SellerReportService {
	
	public SellerReport getSellerReport(Seller Seller);
	
	public SellerReport updateSellerReport(SellerReport sellerReport);

}
