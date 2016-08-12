package com.cusbee.yoki.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Account findByUsername(String username);
	
<<<<<<< HEAD
<<<<<<< HEAD
	@Query(value="SELECT * FROM account a WHERE a.email=?1 AND enabled='Y'", nativeQuery=true)
	Account validateAccount(String email);
	
	@Query(value="SELECT * FROM account a WHERE a.username=?1 AND enabled='Y'", nativeQuery=true)
	Account availability(String username);
	
	@Transactional
	@Query(value="SELECT * FROM account WHERE enabled='Y'", nativeQuery=true)
=======
	@Query(value="SELECT * FROM Account a WHERE a.email=?1 AND enabled='Y'", nativeQuery=true)
=======
	@Query(value="SELECT * FROM account a WHERE a.email=?1 AND enabled='Y'", nativeQuery=true)
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	Account validateAccount(String email);
	
	@Query(value="SELECT * FROM account a WHERE a.username=?1 AND enabled='Y'", nativeQuery=true)
	Account availability(String username);
	
	@Transactional
<<<<<<< HEAD
	@Query(value="SELECT * FROM Account WHERE enabled='Y'", nativeQuery=true)
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	@Query(value="SELECT * FROM account WHERE enabled='Y'", nativeQuery=true)
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	List<Account> findAll();
}
