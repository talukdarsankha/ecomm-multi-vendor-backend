package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyz.models.Category;
import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	
	public Category findByName(String categoryName);
	
}
