package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Account;

public interface AccountDao {
	
	void add(Account user);
	
	List<Account> getAll();
	
	Account get(Long id);

}
