package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;

public interface AccountService {
	
	Account saveAccount(AccountModel request, CrudOperation operations) throws BaseException;
	
	List<Account> getAll();
	
	Account get(Long id);

	Account processActivation(Long id, boolean activate);

	void validateUserEnabled(String username);

	String encryptPassword(String password);
}
