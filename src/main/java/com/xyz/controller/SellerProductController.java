package com.xyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Exception.SellerException;
import com.xyz.Request.CreateProductRequest;
import com.xyz.config.JwtProvider;
import com.xyz.models.Product;
import com.xyz.models.Seller;
import com.xyz.service.ProductService;
import com.xyz.service.SellerService;

@RestController
@RequestMapping("/api/seller/product")
public class SellerProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SellerService sellerService;
	
	
	@GetMapping("/")
	public ResponseEntity<List<Product>> getProductBySellerId(@RequestHeader("Authorization") String jwt) throws SellerException{  
		Seller seller = sellerService.findSellerByJwt(jwt);
		List<Product> sellerProducts = productService.getProductsBySellerID(seller.getId());
		return new ResponseEntity<List<Product>>(sellerProducts, HttpStatus.OK);
	}

	
	@PostMapping("/create")
	public ResponseEntity<Product> createProduct(@RequestHeader("Authorization") String jwt, @RequestBody CreateProductRequest request) throws SellerException{  
		Product product = productService.createProduct(request, sellerService.findSellerByJwt(jwt));
		
		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){  
		try {
			productService.deleteProduct(productId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PutMapping("/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product){  
			Product updatedProduct = productService.updateProduct(productId, product);
			
			return new ResponseEntity<Product>(updatedProduct,HttpStatus.OK);
	}
	
	
}
