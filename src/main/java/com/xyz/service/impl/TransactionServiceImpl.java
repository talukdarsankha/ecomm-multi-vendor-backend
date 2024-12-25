package com.xyz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.models.Seller;
import com.xyz.models.Transaction;
import com.xyz.models.UserOrder;
import com.xyz.repository.PaymentOrderRepository;
import com.xyz.repository.SellerRepository;
import com.xyz.repository.TransactionRepository;
import com.xyz.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	
	

	@Override
	public Transaction createTransaction(UserOrder order) {
		// TODO Auto-generated method stub
		Seller seller = sellerRepository.findById(order.getSellerId()).get();
		
		Transaction transaction = new Transaction();
		transaction.setSeller(seller);
		transaction.setCustomer(order.getUser());
		transaction.setOrder(order);
		return transactionRepository.save(transaction );
	}

	@Override
	public List<Transaction> getTransactionBySeller(Seller seller) {
		// TODO Auto-generated method stub
		return transactionRepository.findBySellerId(seller.getId());
	}

	@Override
	public List<Transaction> getAllTransaction() {
		// TODO Auto-generated method stub
		return transactionRepository.findAll();
	}

}
