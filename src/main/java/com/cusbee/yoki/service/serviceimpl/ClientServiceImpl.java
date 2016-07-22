package com.cusbee.yoki.service.serviceimpl;

import java.util.List;

import com.cusbee.yoki.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.ClientDao;
import com.cusbee.yoki.entity.Client;
import com.cusbee.yoki.exception.BaseException;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientDao dao;
	
	@Override
	public void add(Client client) {
		this.dao.add(client);
	}

	@Override
	public void update(Client client) {
		this.dao.update(client);
	}

	@Override
	public Client get(Long id) throws BaseException {
		return this.dao.get(id);
	}

	@Override
	public List<Client> getAll() {
		return this.dao.getAll();
	}

}
