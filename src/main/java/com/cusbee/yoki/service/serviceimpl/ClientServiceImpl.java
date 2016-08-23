package com.cusbee.yoki.service.serviceimpl;

import java.util.List;

import com.cusbee.yoki.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.ClientDao;
import com.cusbee.yoki.entity.Client;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientDao dao;
	
	@Override
	public Client save(Client client) {
		return dao.save(client);
	}

	@Override
	public Client get(Long id) {
		return dao.get(id);
	}

	@Override
	public List<Client> getAll() {
		return dao.getAll();
	}

	@Override
	public Client getByPhone(String phone) {
		return dao.getByPhone(phone);
	}
}
