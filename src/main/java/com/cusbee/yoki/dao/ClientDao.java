package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Client;

public interface ClientDao {

	Client save(Client client);
	
	Client get(String phone);
	
	List<Client> getAll();
}
