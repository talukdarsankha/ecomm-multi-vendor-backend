package com.xyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyz.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

	public List<Product> findBySellerId(Long sellerId);
	
	@Query("SELECT p from Product p where :query is null or (p.title LIKE %:query% or p.category.name LIKE %:query% )")
	public List<Product> searchProduct(String query);
	
}
