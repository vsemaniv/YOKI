package com.cusbee.yoki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.entity.Client;

@Repository
public interface ClientRepositories extends JpaRepository<Client, Long>{

	Client findById(Long id);
<<<<<<< HEAD
<<<<<<< HEAD

	@Query(value="SELECT c.* FROM client c WHERE c.phone_number=?1", nativeQuery=true)
=======
	
	@Query(value="SELECT c.* FROM Client c WHERE c.phone_number=?1", nativeQuery=true)
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======

	@Query(value="SELECT c.* FROM client c WHERE c.phone_number=?1", nativeQuery=true)
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	Client findByPhoneNumber(String phone);
}
