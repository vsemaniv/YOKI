package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Client;
import com.cusbee.yoki.exception.BaseException;

public interface ClientDao {

<<<<<<< HEAD
<<<<<<< HEAD
	Client save(Client client);
	
	Client get(Long id);
=======
	void add(Client client);
	
	void update(Client client);
	
	Client get(Long id) throws BaseException;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	Client save(Client client);
	
	Client get(Long id);
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	
	List<Client> getAll();
}
