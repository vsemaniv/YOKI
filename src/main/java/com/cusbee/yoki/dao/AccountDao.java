package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Account;

public interface AccountDao {
	
<<<<<<< HEAD
	Account save(Account user);
=======
	void add(Account user);
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	
	List<Account> getAll();
	
	Account get(Long id);

}
