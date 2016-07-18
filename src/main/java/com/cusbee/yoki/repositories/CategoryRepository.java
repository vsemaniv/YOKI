package com.cusbee.yoki.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(String name);
	
	@Query(value="SELECT c.* FROM Category c WHERE enabled='Y'", nativeQuery=true)
	List<Category> findAll();
	
	@Query(value="SELECT c.* FROM Category c WHERE c.id=?1 AND c.enabled='Y'", nativeQuery=true)
	Category findById(Long id);
}
