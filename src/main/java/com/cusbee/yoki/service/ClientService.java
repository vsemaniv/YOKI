package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Client;

public interface ClientService {

	Client save(Client client);

	Client get(String phone);

	List<Client> getAll();
}
