package com.cusbee.yoki.service.serviceimpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.entity.*;
import com.cusbee.yoki.repositories.ClientRepositories;
import com.cusbee.yoki.service.ClientService;
import com.cusbee.yoki.service.CourierService;
import com.cusbee.yoki.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.DishQuantity;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.service.OrderService;
import com.cusbee.yoki.utils.ErrorCodes;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao dao;

    @Autowired
    private DishDao dishDao;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CourierService courierService;

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private ClientRepositories clientRepositories;

    @Override
    public void save(Order order) {
        dao.save(order);
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
    public List<Order> getAvailableOrders() {
        return dao.getAvailableOrders();
    }

    @Override
    public Order get(Long id) {
        validatorService.validateRequestIdNotNull(id);
        Order order = dao.get(id);
        validatorService.validateEntityNotNull(order, Order.class);
        return order;
    }

    public Order saveOrder(OrderModel request, CrudOperation operation) {
        validatorService.validateOrderSaveRequest(request, operation);
        Order order;
        switch (operation) {
            case CREATE:
                order = new Order();
                order.setOrderDate(Calendar.getInstance());
                order.setClient(addClient(request.getClient()));
                order.setStatus(OrderStatus.FRESH);
                break;
            case UPDATE:
                order = get(request.getId());
                break;
            default:
                throw new ApplicationException(ErrorCodes.Common.INVALID_REQUEST,
                        "Invalid Request");
        }
        if(validatorService.isEnumValid(request.getStatus(), OrderStatus.class)) {

        }
        order.setDishes(getDishesFromOrderModel(request));
        order.setAmount(countAmount(request.getDishes()));
        if(validatorService.isEnumValid(request.getStatus(), OrderStatus.class)) {
            order.setStatus(OrderStatus.valueOf(request.getStatus()));
        }

        clientService.add(order.getClient());
        return dao.save(order);
    }

    public Order declineOrder(OrderModel request) {
        validatorService.validateRequestNotNull(request, Order.class);
        Order order = get(request.getId());
        if(validatorService.isEnumValid(request.getStatus(), OrderStatus.class)) {
            order.setStatus(OrderStatus.valueOf(request.getStatus()));
        }
        order.setMessage(order.getMessage());
        return dao.save(order);
    }

    public Order assignCourier(OrderModel request) {
        validatorService.validateRequestNotNull(request, Order.class);
        Order order = get(request.getId());
        order.setCourier(courierService.get(request.getCourierId()));
        return dao.save(order);
    }

    public List<Dish> getDishesFromOrderModel(OrderModel request) {
        List<Dish> dishes = new ArrayList<>();
        for (DishQuantity model : request.getDishes()) {
            Dish dish = dishDao.get(model.getDish().getId());
            if (dish != null)
                dishes.add(dish);
        }
        return dishes;
    }

    private Client addClient(Client request) {
        Client client = clientRepositories.findByPhoneNumber(request.getPhoneNumber());
        if (!Objects.isNull(client)) {
            return client;
        } else {
            client = new Client();
            client.setFirstName(request.getFirstName());
            client.setLocation(request.getLocation());
            client.setPhoneNumber(request.getPhoneNumber());
            clientService.add(client);
            return clientRepositories.findByPhoneNumber(request.getPhoneNumber());
        }
    }

    private Double countAmount(List<DishQuantity> request) {
        Double amount = 0.0;
        for (DishQuantity dish : request) {
            amount += dishDao.get(dish.getDish().getId()).getPrice() * dish.getQuantity();
        }
        return amount;
    }

}
