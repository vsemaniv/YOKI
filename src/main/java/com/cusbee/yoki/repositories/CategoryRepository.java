package com.cusbee.yoki.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(String name);
	
<<<<<<< HEAD
<<<<<<< HEAD
	@Query(value="SELECT c.* FROM category c WHERE enabled='Y'", nativeQuery=true)
	List<Category> findAll();

	//TODO SHOULD WE AVOID EDITING CATEGORY IF ITS BLOCKED? I DONT THINK SO
	@Query(value="SELECT c.* FROM category c WHERE c.id=?1 AND c.enabled='Y'", nativeQuery=true)
=======
	@Query(value="SELECT c.* FROM Category c WHERE enabled='Y'", nativeQuery=true)
	List<Category> findAll();
	
	@Query(value="SELECT c.* FROM Category c WHERE c.id=?1 AND c.enabled='Y'", nativeQuery=true)
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	@Query(value="SELECT c.* FROM category c WHERE enabled='Y'", nativeQuery=true)
	List<Category> findAll();

	//TODO SHOULD WE AVOID EDITING CATEGORY IF ITS BLOCKED? I DONT THINK SO
	@Query(value="SELECT c.* FROM category c WHERE c.id=?1 AND c.enabled='Y'", nativeQuery=true)
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	Category findById(Long id);
}
