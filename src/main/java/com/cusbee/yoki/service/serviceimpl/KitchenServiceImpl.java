package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.repositories.OrderRepository;
import com.cusbee.yoki.service.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KitchenServiceImpl implements KitchenService {

    @Autowired
    OrderRepository repository;

    @Override
    public List<Order> getKitchenOrders() {
        return repository.getKitchenOrders();
    }

}
