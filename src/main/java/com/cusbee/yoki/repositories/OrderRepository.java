package com.cusbee.yoki.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.entity.Order;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long>{

	@Query(value="SELECT o.* FROM orders o WHERE o.id=?1", nativeQuery=true)
	Order findById(Long id);
	
	@Query(value="SELECT o.* FROM orders o WHERE o.order_status='IN_PROGRESS'", nativeQuery=true)
	List<Order> findAllOperatorOrders();
}
