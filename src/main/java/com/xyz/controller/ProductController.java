package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.models.Product;
import com.xyz.repository.SellerRepository;
import com.xyz.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId){
		Product product = productService.findProductById(productId);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	
	@GetMapping("/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam("query") String query){
		List<Product> products = productService.searchProduct(query);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<Page<Product>> getAllProduct(@RequestParam(required = false) String categori, @RequestParam(required = false) String brand,@RequestParam(required = false) String colors, @RequestParam(required = false) String sizes, @RequestParam(required = false) Integer minPrice,
			@RequestParam(required = false) Integer maxPrice, @RequestParam(required = false) Integer minDiscount, @RequestParam(required = false) String sort, @RequestParam(required = false) String stock, @RequestParam(defaultValue = "0") Integer pageNumber){
		Page<Product> products = productService.getAllProduct(categori, brand, colors, sizes, minPrice, maxPrice, minDiscount, sort, stock, pageNumber);
		return new ResponseEntity<Page<Product>>(products, HttpStatus.OK);
	}
	
	
	
	

}
