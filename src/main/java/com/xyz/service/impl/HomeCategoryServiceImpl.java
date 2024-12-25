package com.xyz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.xyz.models.HomeCategory;
import com.xyz.repository.HomeCategoryRepository;
import com.xyz.service.HomeCategoryService;

@Service
public class HomeCategoryServiceImpl implements HomeCategoryService {
	
	@Autowired
	private HomeCategoryRepository homeCategoryRepository;
	
	

	@Override
	public HomeCategory createHomeCategory(HomeCategory homeCategory) {
		// TODO Auto-generated method stub
		return homeCategoryRepository.save(homeCategory);
	}

	@Override
	public List<HomeCategory> createHomeCategories(List<HomeCategory> homeCategories) {
		// TODO Auto-generated method stub
		return homeCategoryRepository.saveAll(homeCategories);
	}

	@Override
	public HomeCategory updateHomeCategory(HomeCategory homeCategory, Long homeCategoryId) {
		// TODO Auto-generated method stub
		HomeCategory existingHomeCategory = homeCategoryRepository.findById(homeCategoryId).get();
		if (homeCategory.getImage()!=null) {
			existingHomeCategory.setImage(homeCategory.getImage());
		}
		if (homeCategory.getName()!=null) {
			existingHomeCategory.setName(homeCategory.getName());
		}
		
		if (homeCategory.getCategoryId()!=null) {
			existingHomeCategory.setCategoryId(homeCategory.getCategoryId());
		}
		
		return homeCategoryRepository.save(existingHomeCategory);
	}

	@Override
	public List<HomeCategory> allHomeCategory() {
		// TODO Auto-generated method stub
		return homeCategoryRepository.findAll();
	}

}
