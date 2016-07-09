package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Account;

public interface UserDao {
	
	void add(Account user);
	
	List<Account> getAll();
	
	Account getById(Long id);

}
