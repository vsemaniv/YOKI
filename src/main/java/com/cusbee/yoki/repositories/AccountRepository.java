package com.cusbee.yoki.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Account findByUsername(String username);
	
	@Query(value="SELECT * FROM account a WHERE a.email=?1 AND enabled='Y'", nativeQuery=true)
	Account getByEmail(String email);
	
	@Query(value="SELECT * FROM account a WHERE a.username=?1 AND enabled='Y'", nativeQuery=true)
	Account availability(String username);
	
	@Transactional
	@Query(value="SELECT * FROM account WHERE enabled='Y'", nativeQuery=true)
	List<Account> findAll();
}
