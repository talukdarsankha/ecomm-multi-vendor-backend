package com.xyz.service;

import java.util.List;

import com.xyz.Exception.SellerException;
import com.xyz.domain.AccountStatus;
import com.xyz.models.Seller;


public interface SellerService {
	
    public Seller findSellerByJwt(String jwt) throws SellerException;
	
	public Seller findSellerByEmail(String email) throws SellerException;
	
	public Seller findSellerById(Long Id) throws SellerException;
	
	public List<Seller> getAllSellers(AccountStatus status);

	public Seller updateSeller(Long id, Seller seller) throws SellerException;
	
	public void deleteSeller(Long id) throws SellerException;
	
	public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws SellerException;
	

}
