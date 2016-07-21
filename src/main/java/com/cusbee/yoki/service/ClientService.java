package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Client;
import com.cusbee.yoki.exception.BaseException;

public interface ClientService {

	void add(Client client);

	void update(Client client);

	Client get(Long id) throws BaseException;

	List<Client> getAll();
}
