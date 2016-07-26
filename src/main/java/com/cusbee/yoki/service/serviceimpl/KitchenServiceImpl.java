package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.OrderStatus;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.repositories.OrderRepository;
import com.cusbee.yoki.service.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KitchenServiceImpl implements KitchenService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDao orderDao;

    @Override
    public List<Order> getKitchenOrders() {
        return orderRepository.getAvailableOrders(OrderStatus.KITCHEN);
    }

    @Override
    public Order passToDriver(Long id) {
        Order order = orderDao.get(id);
        order.setStatus(OrderStatus.DRIVER);
        return orderDao.save(order);
    }

    @Override
    public Order canNotPrepare(Long id) {
        Order order = orderDao.get(id);
        order.setStatus(OrderStatus.CANT_PREPARE);
        return orderDao.save(order);
    }
}
