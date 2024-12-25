package com.xyz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.models.Deal;
import com.xyz.models.HomeCategory;
import com.xyz.repository.DealRepository;
import com.xyz.repository.HomeCategoryRepository;
import com.xyz.repository.UserRepository;
import com.xyz.service.DealService;


@Service
public class DealServiceImpl implements DealService {
	
	@Autowired
	private DealRepository dealRepository;
	
	@Autowired
	private HomeCategoryRepository homeCategoryRepository;
	
	
	

	@Override
	public List<Deal> getAllDeals() {
		// TODO Auto-generated method stub
		return dealRepository.findAll();
	}

	@Override
	public Deal createDeal(Deal deal) {
		// TODO Auto-generated method stub
		HomeCategory homeCategory = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);  
        deal.setCategory(homeCategory);
		return dealRepository.save(deal);
	}

	@Override
	public Deal updateDeal(Deal deal, Long dealId) throws Exception {
		// TODO Auto-generated method stub
		Deal existingDeal = dealRepository.findById(dealId).get();
		HomeCategory homeCategory = homeCategoryRepository.findById(deal.getCategory().getId()).get();  

		if (existingDeal!=null) {
			if (deal.getDiscount()!=null) {
				existingDeal.setDiscount(deal.getDiscount());
			}
			
			if (homeCategory!=null) {
				existingDeal.setCategory(homeCategory);
			}
		 return dealRepository.save(existingDeal);
		}
		throw new Exception("deal not found...");
	}

	@Override
	public void deleteDeal(Long dealId) {
		// TODO Auto-generated method stub
		Deal deal= dealRepository.findById(dealId).get();
		dealRepository.delete(deal);
	}

}
