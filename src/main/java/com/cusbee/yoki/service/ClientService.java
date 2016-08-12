package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Client;
import com.cusbee.yoki.exception.BaseException;

public interface ClientService {

<<<<<<< HEAD
	Client save(Client client);

	Client get(Long id);
=======
	void add(Client client);

	void update(Client client);

	Client get(Long id) throws BaseException;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280

	List<Client> getAll();
}
