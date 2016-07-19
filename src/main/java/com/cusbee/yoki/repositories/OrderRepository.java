package com.cusbee.yoki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.entity.Order;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long>{

	@Query(value="SELECT o.* FROM Order o WHERE o.id=?1", nativeQuery=true)
	Order findById(Long id);
}
