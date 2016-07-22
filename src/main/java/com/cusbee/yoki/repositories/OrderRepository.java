package com.cusbee.yoki.repositories;

import com.cusbee.yoki.entity.OrderStatus;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.entity.Order;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long>{

	@Query(value = "SELECT o.* FROM orders WHERE o.order_status=?1", nativeQuery = true)
	List<Order> getAvailableOrders(OrderStatus status);

	@Query(value="SELECT o.* FROM orders o WHERE o.id=?1", nativeQuery=true)
	Order findById(Long id);
}
