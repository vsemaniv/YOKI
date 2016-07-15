package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;

public interface AccountService {

	void add(Account user);
	
	Account parse(AccountModel request,  CrudOperation operations) throws BaseException;
	
	List<Account> getAll();
	
	Account get(Long id);

	void activation(Long id, CrudOperation operation) throws BaseException;

	void availability(String username) throws BaseException; 
	
	void isNull(Account user) throws BaseException;
}
