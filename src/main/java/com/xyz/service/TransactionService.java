package com.xyz.service;

import java.util.List;

import com.xyz.models.Seller;
import com.xyz.models.Transaction;
import com.xyz.models.UserOrder;

public interface TransactionService {
	
	public Transaction createTransaction(UserOrder order);
	
	public List<Transaction> getTransactionBySeller(Seller seller);
	
	public List<Transaction> getAllTransaction();

}
