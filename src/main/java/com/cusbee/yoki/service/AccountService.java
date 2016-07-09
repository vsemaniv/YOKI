package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.utils.AccountOperations;

public interface AccountService {

	void add(Account user);
	
	Account parse(AccountModel request,  AccountOperations operations) throws BaseException;
	
	List<Account> getAll();
	
	Account getById(Long id);

	void activation(Account user, AccountOperations operation) throws BaseException;

	void availability(String username) throws BaseException; 
	
	void isNull(Account user) throws BaseException;
}
