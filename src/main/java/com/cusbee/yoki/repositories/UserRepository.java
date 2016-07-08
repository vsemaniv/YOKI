package com.cusbee.yoki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cusbee.yoki.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);
	
	@Query(value="SELECT * FROM User u WHERE u.email=?1 AND enabled=true", nativeQuery=true)
	User validateAccount(String email);
	
	@Query(value="SELECT * FROM User u WHERE u.username=?1 AND enabled=true", nativeQuery=true)
	User availability(String username);
}
