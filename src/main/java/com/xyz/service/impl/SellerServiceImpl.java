package com.xyz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xyz.Exception.SellerException;
import com.xyz.config.JwtProvider;
import com.xyz.domain.AccountStatus;
import com.xyz.domain.USER_ROLE;
import com.xyz.models.Address;
import com.xyz.models.Seller;
import com.xyz.models.User;
import com.xyz.repository.AddressRepository;
import com.xyz.repository.SellerRepository;
import com.xyz.repository.UserRepository;
import com.xyz.service.SellerService;


@Service
public class SellerServiceImpl implements SellerService {
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Seller findSellerByJwt(String jwt) throws SellerException {
		// TODO Auto-generated method stub
		
		 String email = jwtProvider.getEmailFromToken(jwt);
		 return findSellerByEmail(email);
		
	}

	@Override
	public Seller findSellerByEmail(String email) throws SellerException {
		// TODO Auto-generated method stub
		Seller seller = sellerRepository.findByEmail(email);
		 
		 if (seller==null) {
			throw new SellerException("Seller Not Found With This Email : "+email);
		}
		 return seller;
	}

	@Override
	public Seller findSellerById(Long Id) throws SellerException {
		return sellerRepository.findById(Id).orElseThrow(()-> new SellerException("Seller Not Found With This Id : "+Id));
		
	}

	

	@Override
	public List<Seller> getAllSellers(AccountStatus status) {
		// TODO Auto-generated method stub
		return sellerRepository.findByAccountStatus(status);
	}

	@Override
	public Seller updateSeller(Long id, Seller seller) throws SellerException  {
		// TODO Auto-generated method stub
		Seller existSeller = findSellerById(id);
		
		if (seller.getSellerName()!=null) {
			existSeller.setSellerName(seller.getSellerName());
		}
		
		if (seller.getMobile()!=null) {
			existSeller.setMobile(seller.getMobile());
		}
		
		if (seller.getBusinessDetails()!=null) {
			if (seller.getBusinessDetails().getBusinessName()!=null) {
				existSeller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());   
			}
		}
		
		if (seller.getBankDetails()!=null) {
			if (seller.getBankDetails().getAccountHolderName()!=null) {
				existSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());   
			}
			
			if (seller.getBankDetails().getAccountNumber()!=null) {
				existSeller.getBankDetails().setAccountNumber(seller.getBankDetails().getAccountNumber());   
			}
			
			if (seller.getBankDetails().getIfscCode()!=null) {
				existSeller.getBankDetails().setIfscCode(seller.getBankDetails().getIfscCode());   
			}
		}
		
		if (seller.getPickUpAddress()!=null) {
			if (seller.getPickUpAddress().getAddress()!=null) {
				existSeller.getPickUpAddress().setAddress(seller.getPickUpAddress().getAddress());   
			}
			
			if (seller.getPickUpAddress().getCity()!=null) {
				existSeller.getPickUpAddress().setCity(seller.getPickUpAddress().getCity());   
			}
			
			if (seller.getPickUpAddress().getState()!=null) {
				existSeller.getPickUpAddress().setState(seller.getPickUpAddress().getState());   
			}
			
			if (seller.getPickUpAddress().getMobile()!=null) {
				existSeller.getPickUpAddress().setMobile(seller.getPickUpAddress().getMobile());   
			}
			
		}
		
		if (seller.getGstin()!=null) {
			existSeller.setGstin(seller.getGstin());   
		}
		
		
		return sellerRepository.save(existSeller);
	}

	@Override
	public void deleteSeller(Long id) throws SellerException {
		// TODO Auto-generated method stub
		Seller existSeller = findSellerById(id);
		sellerRepository.delete(existSeller);
		
	}

	
	@Override
	public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws SellerException {
		Seller existSeller = findSellerById(sellerId);
		existSeller.setAccountStatus(status);
		return sellerRepository.save(existSeller);
	}

}
