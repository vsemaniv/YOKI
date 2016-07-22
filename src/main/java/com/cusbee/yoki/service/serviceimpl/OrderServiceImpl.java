package com.cusbee.yoki.service.serviceimpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import com.cusbee.yoki.repositories.ClientRepositories;
import com.cusbee.yoki.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.Client;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.OrderStatus;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.DishQuantity;
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

    @Autowired
    private ClientService clientService;

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
                if (Objects.isNull(request.getClient())) {
                    throw new ApplicationException(ErrorCodes.Order.INVALID_REQUEST, "Object Client are empty");
                }
                order = new Order();
                order.setDishes(getDishesFromOrderModel(request));
                order.setOrderDate(Calendar.getInstance());
                order.setAmount(countAmount(request.getDishes()));
                order.setStatus(OrderStatus.IN_PROGRESS);
                order.setClient(addClient(request.getClient()));
                clientService.add(order.getClient());
                return order;
            case UPDATE:
                order = repository.findById(request.getId());
                return order;
            default:
                throw new ApplicationException(ErrorCodes.Common.INVALID_REQUEST,
                        "Invalid Request");
        }
    }

    protected Client addClient(Client request) {
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

    public List<Dish> getDishesFromOrderModel(OrderModel request) {
        List<Dish> dishes = new ArrayList<>();
        for (DishQuantity model : request.getDishes()) {
            Dish dish = dishRepository.findById(model.getDish().getId());
            if (dish != null)
                dishes.add(dish);
        }
        return dishes;
    }

    protected Double countAmount(List<DishQuantity> request) {
        Double amount = 0.0;
        for (DishQuantity dish : request) {
            amount += dishRepository.findById(dish.getDish().getId()).getPrice() * dish.getQuantity();
        }
        return amount;
    }

}
