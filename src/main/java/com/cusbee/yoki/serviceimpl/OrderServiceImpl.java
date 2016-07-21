package com.cusbee.yoki.serviceimpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.OrderStatus;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.DishModel;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.repositories.DishRepository;
import com.cusbee.yoki.repositories.OrderRepository;
import com.cusbee.yoki.service.OrderService;
import com.cusbee.yoki.utils.ErrorCodes;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao dao;
	
	@Autowired
	private DishRepository dishRepository;
	
	@Autowired
	private OrderRepository repository;

	@Override
	public void add(Order order) {
		dao.add(order);
	}

	@Override
	public void update(Order order) {
		dao.update(order);
	}

	@Override
	public void remove(Order order) {
		dao.remove(order);
	}

	@Override
	public List<Order> getAll() {
		return dao.getAll();
	}

	@Override
	public Order get(Long id) {
		return dao.get(id);
	}

	public Order parse(OrderModel request, CrudOperation operation)
			throws BaseException {
		Order order;
		switch (operation) {
		case CREATE:
			if (Objects.isNull(request.getDishes())) {
				throw new ApplicationException(
						ErrorCodes.Order.EMPTY_LIST_OF_DISHES,
						"List of ordered dishes are empty");
			}
			order = new Order();
			order.setDishes(getDishesFromModel(request));
			order.setOrderDate(Calendar.getInstance());
			order.setAmount(countAmount(order.getDishes()));
			order.setStatus(OrderStatus.IN_PROGRESS);
			return order;
		case UPDATE:
			if(Objects.isNull(request.getId())){
				throw new ApplicationException(ErrorCodes.Order.EMPTY_FIELD, "Field ID is not present");
			}
			if (Objects.isNull(request.getDishes())) {
				throw new ApplicationException(
						ErrorCodes.Order.EMPTY_LIST_OF_DISHES,
						"List of ordered dishes are empty");
			}
			
			order = repository.findById(request.getId());
			return order;
		default:
			throw new ApplicationException(ErrorCodes.Common.INVALID_REQUEST,
					"Invalid Requet");
		}
	}
	
	protected List<Dish> getDishesFromModel(OrderModel request) {
		List<Dish> dishes = new ArrayList<>();
		for(DishModel model : request.getDishes()){
			Dish dish = dishRepository.findById(model.getId());
			 if(dish!=null)
				 dishes.add(dish);
		}
		return dishes;
	}
	
	protected Double countAmount(List<Dish> dishes){
		Double amount = 0.0;
		for(Dish dish: dishes){
			amount +=dish.getPrice();
		}
		return amount;
	}

}
