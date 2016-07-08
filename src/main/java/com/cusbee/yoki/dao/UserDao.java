package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.User;

public interface UserDao {
	
	void add(User user);
	
	List<User> getAll();
	
	User getById(Long id);

}
