package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.OrderStatus;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.DishModel;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.repositories.OrderRepository;
import com.cusbee.yoki.service.OperatorService;
import com.cusbee.yoki.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDao orderDao;

    @Override
    public List<Order> getAllNonProcessedOrders() {
        return orderRepository.getAvailableOrders(OrderStatus.IN_PROGRESS);
    }

    @Override
    public Order processOrder(OrderModel request, boolean accept) throws BaseException {
        Order order = orderRepository.findById(request.getId());
        if(order != null) {
            if(accept) {
                order.setAmount(request.getAmount());
                order.setClient(request.getClient());
                order.setDishes(orderService.getDishesFromOrderModel(request));
                order.setMessage(request.getMessage());
                order.setStatus(OrderStatus.KITCHEN);
                return order;
            } else {
                order.setStatus(OrderStatus.DECLINED);
            }
            orderDao.save(order);
        }
        throw new ApplicationException(6004, "Could not find order with such id");
    }
}
