package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Account;
<<<<<<< HEAD
<<<<<<< HEAD
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.model.AccountModel;

public interface AccountService {
	
	Account saveAccount(AccountModel request, CrudOperation operations);
=======
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.BaseException;
=======
import com.cusbee.yoki.entity.enums.CrudOperation;
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
import com.cusbee.yoki.model.AccountModel;

public interface AccountService {
	
<<<<<<< HEAD
	Account parse(AccountModel request,  CrudOperation operations) throws BaseException;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	Account saveAccount(AccountModel request, CrudOperation operations);
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	
	List<Account> getAll();
	
	Account get(Long id);

<<<<<<< HEAD
<<<<<<< HEAD
	Account processActivation(Long id, boolean activate);

	void validateUserEnabled(String username);

	String encryptPassword(String password);
=======
	void activation(Long id, CrudOperation operation) throws BaseException;

	void availability(String username) throws BaseException; 
	
	void isNull(Account user) throws BaseException;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	Account processActivation(Long id, boolean activate);

	void validateUserEnabled(String username);

	String encryptPassword(String password);
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
}
