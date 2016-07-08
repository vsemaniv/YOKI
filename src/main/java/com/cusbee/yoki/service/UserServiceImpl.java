package com.cusbee.yoki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.UserDao;
import com.cusbee.yoki.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void add(User user) {
		this.userDao.add(user);
	}

}
