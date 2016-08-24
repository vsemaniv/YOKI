package com.cusbee.yoki.service.serviceimpl;

import java.util.List;

import com.cusbee.yoki.service.ClientService;
import com.cusbee.yoki.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.ClientDao;
import com.cusbee.yoki.entity.Client;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	@Autowired
	ValidatorService validatorService;

	@Autowired
	private ClientDao dao;
	
	@Override
	public Client save(Client client) {
		return dao.save(client);
	}

	@Override
	public Client get(String phone) {
		Client client = dao.get(phone);
		validatorService.validateEntityNotNull(client, Client.class);
		return client;
	}

	@Override
	public List<Client> getAll() {
		return dao.getAll();
	}
}
