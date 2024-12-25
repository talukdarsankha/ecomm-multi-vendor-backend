package com.xyz.service;

import java.util.List;

import com.xyz.models.Deal;

public interface DealService {
	
	public List<Deal> getAllDeals();
	
	public Deal createDeal(Deal deal);
	
	public Deal updateDeal(Deal deal, Long dealId) throws Exception;
	
	public void deleteDeal(Long dealId);

}
