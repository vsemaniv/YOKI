package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Client;
import com.cusbee.yoki.exception.BaseException;

public interface ClientService {

	Client save(Client client);

	Client get(Long id);

	List<Client> getAll();
}
