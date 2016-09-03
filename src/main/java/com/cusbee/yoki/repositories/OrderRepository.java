package com.cusbee.yoki.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.entity.Order;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long>{

	@Query(value = "SELECT o.* FROM orders o WHERE o.order_status <> 'CLOSED'", nativeQuery = true)
	List<Order> getAvailableOrders();

	@Query(value = "SELECT o.* FROM orders o WHERE o.order_status IN ('COOKING', 'PREPARED')", nativeQuery = true)
	List<Order> getKitchenOrders();

	@Query(value="SELECT o.* FROM orders o WHERE o.id=?1", nativeQuery=true)
	Order findById(Long id);

	@Query(value="SELECT o.* FROM orders o WHERE o.order_date BETWEEN ?1 AND ?2", nativeQuery=true)
	List<Order> getOrderHistory(String startDate, String endDate);

	@Query(value="SELECT o.* FROM orders o WHERE o.client_phone = ?3 AND o.order_date BETWEEN ?1 AND ?2", nativeQuery=true)
	List<Order> getOrderHistory(String startDate, String endDate, String client);

	@Query(value="SELECT o.* FROM orders o WHERE o.client_phone = ?1", nativeQuery=true)
	List<Order> getOrderHistory(String client);

	@Query(value="SELECT o.* FROM orders o LIMIT 1", nativeQuery = true)
	Order getActualOrderForCourier(Long courierId);
}
