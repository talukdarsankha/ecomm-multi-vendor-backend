package com.xyz.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.xyz.Request.CreateProductRequest;
import com.xyz.models.Product;
import com.xyz.models.Seller;

public interface ProductService {
	
	public Product createProduct(CreateProductRequest request, Seller seller);
	
	public Product updateProduct(Long productId,Product product);
	
	public void deleteProduct(Long productId);
	
	public Product findProductById(Long productId);
	
	public List<Product> searchProduct(String query);
	
	
	public Page<Product> getAllProduct(String categori, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice,Integer minDiscount, String sort, String stock ,Integer pageNumber);
	
	
	public List<Product> getProductsBySellerID(Long sellerID);

}
