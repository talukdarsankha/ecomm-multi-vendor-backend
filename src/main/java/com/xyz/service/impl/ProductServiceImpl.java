package com.xyz.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.xyz.Request.CreateProductRequest;
import com.xyz.models.Category;
import com.xyz.models.Product;
import com.xyz.models.Seller;
import com.xyz.repository.CategoryRepository;
import com.xyz.repository.ProductRepository;
import com.xyz.service.ProductService;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;


@Service
public class ProductServiceImpl implements ProductService {
	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Product createProduct(CreateProductRequest request, Seller seller) {
		// TODO Auto-generated method stub
		
		Category category1 = categoryRepository.findByName(request.getCategory1());
		if (category1==null) {
			Category category = new Category();
			category.setName(request.getCategory1());
			category.setLavel(1);
			category1 = categoryRepository.save(category);
		}
		
		Category category2 = categoryRepository.findByName(request.getCategory2());
		if (category2==null) {
			Category category = new Category();
			category.setName(request.getCategory2());
			category.setLavel(2);
			category.setParentCategory(category1);
			
			category2 = categoryRepository.save(category);
		}
		
		Category category3 = categoryRepository.findByName(request.getCategory3());
		if (category3==null) {
			Category category = new Category();
			category.setName(request.getCategory3());
			category.setLavel(3);
			category.setParentCategory(category2);
			
			category3 = categoryRepository.save(category);
		}
		
		
		int discountPrecent = calculateDiscountPercent(request.getMrpPrice(), request.getSellingPrice());
		
		
		Product product = new Product();
		product.setSeller(seller);
		
		product.setCategory(category3);
		product.setDescription(request.getDescription());
		product.setCreatedAt(LocalDateTime.now());
		product.setTitle(request.getTitle());
		product.setColor(request.getColor());
		product.setSellingPrice(request.getSellingPrice());
		product.setImages(request.getImages());
		product.setMrpPrice(request.getMrpPrice());
		product.setSizes(request.getSizes());
		product.setDiscountedPercent(discountPrecent);
		
		return productRepository.save(product);
	}
	
	
	
	private int calculateDiscountPercent(int mrpPrice, int sellingPrice) {
		if (mrpPrice<=0) {
//			throw new IllegalArgumentException("ACtual Price mube be greater that 0");
			return 0;
		}
		double discount = mrpPrice-sellingPrice;
		double discountPercent = (discount/mrpPrice)*100;
		
		return (int) discountPercent;
	}
	
	
	

	@Override
	public Product updateProduct(Long productId, Product product) {
		// TODO Auto-generated method stub
		product.setId(productId);
		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(Long productId) {
		// TODO Auto-generated method stub
		Product product = findProductById(productId);
		productRepository.delete(product);
	}

	@Override
	public Product findProductById(Long productId) {
		// TODO Auto-generated method stub
		Product product = productRepository.findById(productId).get();
		return product;
	}

	@Override
	public List<Product> searchProduct(String query) {
		// TODO Auto-generated method stub
		return productRepository.searchProduct(query);
	}

	@Override
	public Page<Product> getAllProduct(String categori, String brand, String colors, String sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {
		// TODO Auto-generated method stub
		
		Specification<Product> spec = (root,query,criteriaBuilder)->{
			List<Predicate> predicates = new ArrayList<>();
			
			if (categori!=null) {
				Join<Product, Category> productCategoryjoinTable = root.join("category"); // this category should be same name from product model category parameter  
				predicates.add(criteriaBuilder.equal(productCategoryjoinTable.get("name"),categori));
			}
			
			if (colors!=null && !colors.isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("color"),colors));
			}
				
			if (sizes!=null && !sizes.isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("sizes"),sizes));
			}
			
			
			if (minPrice!=null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"),minPrice));
			}
			
			if (maxPrice!=null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"),maxPrice));
			}
			
			if (minDiscount!=null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountedPercent"),minDiscount));
			}
			
			if (stock != null && !stock.isEmpty()) {
			    predicates.add(root.get("stock").in(stock));
			}



			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			
		};
		
		Pageable pageable;
		
		if (sort !=null && !sort.isEmpty()) {
			switch (sort) {
			case "price_low": {
				pageable = PageRequest.of(pageNumber!=null ? pageNumber : 0, 10, Sort.by("sellingPrice").ascending());				
			   break;
			}
			case "price_high": {
				pageable = PageRequest.of(pageNumber!=null ? pageNumber : 0, 10, Sort.by("sellingPrice").descending());				
			   break;
			}
			default:
				pageable = PageRequest.of(pageNumber!=null ? pageNumber : 0, 10, Sort.unsorted());
			  break;
			}
		}else {
			pageable = PageRequest.of(pageNumber!=null ? pageNumber  : 0, 10, Sort.unsorted());
		}
		
		return productRepository.findAll(spec, pageable);
	}

	@Override
	public List<Product> getProductsBySellerID(Long sellerID) {
		// TODO Auto-generated method stub
		return productRepository.findBySellerId(sellerID);
	}

}
