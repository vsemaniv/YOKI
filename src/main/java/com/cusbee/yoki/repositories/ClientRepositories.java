package com.cusbee.yoki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.entity.Client;

@Repository
public interface ClientRepositories extends JpaRepository<Client, Long>{

	Client findById(Long id);

	@Query(value="SELECT c.* FROM client c WHERE c.phone_number=?1", nativeQuery=true)
	Client findByPhoneNumber(String phone);
}
