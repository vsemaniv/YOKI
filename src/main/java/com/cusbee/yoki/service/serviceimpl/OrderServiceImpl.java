package com.cusbee.yoki.service.serviceimpl;

import java.util.*;

import com.cusbee.yoki.entity.*;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.model.ClientModel;
import com.cusbee.yoki.repositories.OrderRepository;
import com.cusbee.yoki.service.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.DishQuantityModel;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.utils.ErrorCodes;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao dao;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private DishService dishService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CourierService courierService;

    @Autowired
    private ValidatorService validatorService;

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
    public List<Order> getOrderHistory(String startDate, String endDate, String clientId) {
        if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            validatorService.validateDates(startDate, endDate);
            if(StringUtils.isEmpty(clientId)) {
                return repository.getOrderHistory(startDate, endDate);
            } else {
                Client client = clientService.get(clientId);
                return repository.getOrderHistory(startDate, endDate, clientId);
            }
        } else if(StringUtils.isNotEmpty(clientId)) {
            Client client = clientService.get(clientId);
            return repository.getOrderHistory(clientId);
        } else {
            throw new ApplicationException(ErrorCodes.Order.NO_CRITERIA_FOR_HISTORY,
                    "You should specify at least one of the following: both dates or client ID");
        }
    }

    @Override
    public Order get(Long id) {
        validatorService.validateRequestIdNotNull(id, Order.class);
        Order order = dao.get(id);
        validatorService.validateEntityNotNull(order, Order.class);
        return order;
    }

    @Override
    public Order saveOrder(OrderModel request, CrudOperation operation) {
        validatorService.validateOrderSaveRequest(request, operation);
        Order order;
        switch (operation) {
            case CREATE:
                order = new Order();
                order.setOrderDate(Calendar.getInstance());
                order.setStatus(OrderStatus.FRESH);
                order.setClient(parseClient(request.getClient(), new Client()));
                break;
            case UPDATE:
                order = get(request.getId());
                order.setClient(parseClient(request.getClient(), clientService.get(request.getClient().getPhone())));
                if(request.getCourierId() != null) {
                    order.setCourier(courierService.get(request.getCourierId()));
                }
                if(request.getTimeToTake() != null){
                	order.getTimeToTake().setTime(request.getTimeToTake());
                }
                if(request.getTimeToDeliver() != null){
                	order.getTimeToDeliver().setTime(request.getTimeToDeliver());
                }
                if(request.getTimeTaken() != null){
                    order.getTimeTaken().setTime(request.getTimeTaken());
                }
                if(request.getTimeDelivered() != null){
                	order.getTimeDelivered().setTime(request.getTimeDelivered());
                }
                if(validatorService.isEnumValid(request.getStatus(), OrderStatus.class)) {
                    order.setStatus(OrderStatus.valueOf(request.getStatus()));
                }
                break;
            default:
                throw new ApplicationException(ErrorCodes.Common.INVALID_REQUEST,
                        "Invalid Request");
        }
        if(request.getDishes() != null) {
            List<DishQuantityModel> dishModels = request.getDishes();
            resetDishes(order, dishModels);
        }
        order.setCost(countAmount(request.getDishes()));
        return dao.save(order);
    }

    @Override
    public Order declineOrder(OrderModel request) {
        validatorService.validateRequestNotNull(request, Order.class);
        Order order = get(request.getId());
        if(validatorService.isEnumValid(request.getStatus(), OrderStatus.class)) {
            order.setStatus(OrderStatus.valueOf(request.getStatus()));
        } else {
            throw new ApplicationException(ErrorCodes.Order.INVALID_STATUS,
                    "Invalid order status");
        }
        if(StringUtils.isNotEmpty(request.getMessage())) {
            order.setMessage(request.getMessage());
        } else {
            throw new ApplicationException(ErrorCodes.Order.EMPTY_DECLINE_MESSAGE,
                    "Decline message should not be empty!");
        }
        return dao.save(order);
    }

    @Override
    public Order assignCourier(OrderModel request) {
        validatorService.validateRequestNotNull(request, Order.class);
        Order order = get(request.getId());
        order.setCourier(courierService.get(request.getCourierId()));
        return dao.save(order);
    }

    @Override
    public Order saveOrderStatus(Long id, OrderStatus status) {
        Order order = get(id);
        order.setStatus(status);
        return dao.save(order);
    }

    /**
     * Rewrites all dishes in the order.
     * At first this method clears list of dishes making it empty.
     * Then it adds to order every dish that came in order model from UI.
     *
     * @param order         - old order version retrieved from database
     * @param dishModels    - dish models received from front-end
     */
    private void resetDishes(Order order, List<DishQuantityModel> dishModels) {
        List<DishQuantity> dishes = order.getDishes();
        dishes.clear();
        for (DishQuantityModel model : dishModels) {
            Dish dish = dishService.get(model.getDishId());
            dishes.add(new DishQuantity(order, dish, model.getQuantity()));
        }
    }

    /**
     * Takes data about customer from order and tries to find
     * a client with such phone number in the database. If there
     * is no such client we create and return new one with the data
     * passed from order.
     *
     * @param customerData - information from order about customer.
     * @return client instance.
     */
    private Client parseClient(ClientModel customerData, Client client) {
        client.setPhoneNumber(customerData.getPhone());
        client.setName(customerData.getName());
        client.setAddress(customerData.getAddress());
        return client;
    }

    private Double countAmount(List<DishQuantityModel> request) {
        Double amount = 0.0;
        for (DishQuantityModel dishPosition : request) {
            amount += dishService.get(dishPosition.getDishId()).getPrice() * dishPosition.getQuantity();
        }
        return amount;
    }
}
