package com.xyz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.domain.HomeCategorySection;
import com.xyz.models.Deal;
import com.xyz.models.Home;
import com.xyz.models.HomeCategory;
import com.xyz.repository.DealRepository;
import com.xyz.repository.HomeCategoryRepository;
import com.xyz.service.HomeService;


@Service
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	private DealRepository dealRepository;
	

	@Override
	public Home createHomePageData(List<HomeCategory> allCategories) {
		// TODO Auto-generated method stub
		List<HomeCategory> girdCategories = allCategories.stream()
				.filter(category->category.getHomeCategorySection() == HomeCategorySection.GRID)
				.collect(Collectors.toList());
		
		List<HomeCategory> shopByCategories = allCategories.stream()
				.filter(category->category.getHomeCategorySection() == HomeCategorySection.SHOP_BY_CATEGORY)
				.collect(Collectors.toList());
		
		List<HomeCategory> electronicsCategories = allCategories.stream()
				.filter(category->category.getHomeCategorySection() == HomeCategorySection.ELECTRIC_CATEGORY)
				.collect(Collectors.toList());
		
		List<HomeCategory> dealCategories = allCategories.stream()
				.filter(category->category.getHomeCategorySection() == HomeCategorySection.DEAL)
				.toList();
		
		List<Deal> createdDeals = new ArrayList<>();
		
		if (dealRepository.findAll().isEmpty()) {
			List<Deal> deals = allCategories.stream()
					.filter(category->category.getHomeCategorySection()==HomeCategorySection.DEAL)
					.map(category-> new Deal(null, 10, category))
					.collect(Collectors.toList());
			createdDeals = dealRepository.saveAll(deals);
		}else {
			createdDeals = dealRepository.findAll();
		}
		
		Home home = new Home();
		home.setGrid(girdCategories);
		home.setShopByCategories(shopByCategories);
		home.setElectricCategories(electronicsCategories);
		home.setDealCategories(dealCategories);
		home.setDeals(createdDeals);
		
		return home;
	}

}
