package com.cusbee.yoki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cusbee.yoki.entity.Account;

public interface UserRepository extends JpaRepository<Account, Long>{
	
	Account findByUsername(String username);
	
	@Query(value="SELECT * FROM User u WHERE u.email=?1 AND enabled=true", nativeQuery=true)
	Account validateAccount(String email);
	
	@Query(value="SELECT * FROM User u WHERE u.username=?1 AND enabled=true", nativeQuery=true)
	Account availability(String username);
}
