package com.cusbee.yoki.service;

import java.util.List;

<<<<<<< HEAD
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.Order;
=======
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.exception.BaseException;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
import com.cusbee.yoki.model.OrderModel;

public interface OrderService {

<<<<<<< HEAD
	void save(Order order);
=======
	void add(Order order);

	void update(Order order);
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280

	void remove(Order order);

	List<Order> getAll();

<<<<<<< HEAD
	List<Order> getAvailableOrders();

	Order get(Long id);
	
	Order saveOrder(OrderModel request, CrudOperation operation);

	Order declineOrder(OrderModel request);

	Order assignCourier(OrderModel request);

	List<Dish> getDishesFromOrderModel(OrderModel request);
=======
	Order get(Long id);
	
	Order parse(OrderModel request, CrudOperation operation)
			throws BaseException;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
}
