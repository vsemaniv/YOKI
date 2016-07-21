package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Client;
import com.cusbee.yoki.exception.BaseException;

public interface ClientDao {

	void add(Client client);
	
	void update(Client client);
	
	Client get(Long id) throws BaseException;
	
	List<Client> getAll();
}
