package com.cusbee.yoki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cusbee.yoki.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);
	
}
