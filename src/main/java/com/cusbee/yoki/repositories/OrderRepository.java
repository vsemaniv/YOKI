package com.cusbee.yoki.repositories;

<<<<<<< HEAD
<<<<<<< HEAD
import com.cusbee.yoki.entity.enums.OrderStatus;
=======
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
import com.cusbee.yoki.entity.enums.OrderStatus;
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.entity.Order;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long>{

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	@Query(value = "SELECT o.* FROM orders WHERE o.order_status=?1", nativeQuery = true)
	List<Order> getAvailableOrders(OrderStatus status);

	@Query(value = "SELECT o.* FROM orders WHERE o.order_status IN ('OPERATOR','CANT_PREPARE')", nativeQuery = true)
	List<Order> getOperatorOrders();

<<<<<<< HEAD
	@Query(value="SELECT o.* FROM orders o WHERE o.id=?1", nativeQuery=true)
	Order findById(Long id);
=======
	@Query(value="SELECT o.* FROM orders o WHERE o.id=?1", nativeQuery=true)
	Order findById(Long id);
	
	@Query(value="SELECT o.* FROM orders o WHERE o.order_status='IN_PROGRESS'", nativeQuery=true)
	List<Order> findAllOperatorOrders();
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	@Query(value="SELECT o.* FROM orders o WHERE o.id=?1", nativeQuery=true)
	Order findById(Long id);
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
}
