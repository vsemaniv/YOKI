package com.cusbee.yoki.repositories;

import java.util.List;

import com.cusbee.yoki.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Account findByUsername(String username);
	
	@Query(value="SELECT * FROM account a WHERE a.email=?1 AND enabled='Y'", nativeQuery=true)
	Account validateAccount(String email);
	
	@Query(value="SELECT * FROM account a WHERE a.username=?1 AND enabled='Y'", nativeQuery=true)
	Account availability(String username);

	@Query(value="SELECT * FROM account a, courier c WHERE a.id=?1 AND a.id = c.account_id AND a.enabled='Y' AND c.courier_status IN (?2)", nativeQuery=true)
	Courier findCourierById(Long id, Courier.CourierStatus... status);
	
	@Transactional
	@Query(value="SELECT * FROM account WHERE enabled='Y'", nativeQuery=true)
	List<Account> findAll();
}
