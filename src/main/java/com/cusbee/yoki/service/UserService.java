package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.User;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.UserModel;
import com.cusbee.yoki.utils.AccountOperations;

public interface UserService {

	void add(User user);
	
	User parse(UserModel request,  AccountOperations operations) throws BaseException;
	
	List<User> getAll();
	
	User getById(Long id);

	void activation(User user, AccountOperations operation) throws BaseException;

	void availability(String username) throws BaseException; 
}
