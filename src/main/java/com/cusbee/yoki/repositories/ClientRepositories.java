package com.cusbee.yoki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.entity.Client;

@Repository
public interface ClientRepositories extends JpaRepository<Client, Long>{

	Client findById(Long id);
}
