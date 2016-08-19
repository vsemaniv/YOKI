package com.cusbee.yoki.service.serviceimpl;

import java.util.*;

import com.cusbee.yoki.entity.*;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.model.ClientModel;
import com.cusbee.yoki.repositories.ClientRepositories;
import com.cusbee.yoki.repositories.DishQuantityRepository;
import com.cusbee.yoki.service.*;

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
    private DishService dishService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CourierService courierService;

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private ClientRepositories clientRepositories;

    @Autowired
    private DishQuantityRepository dishQuantityRepository;

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
    public List<Order> getOrderHistory(String startDate, String endDate) {
        validatorService.validateDates(startDate, endDate);
        return dao.getOrderHistory(startDate, endDate);
    }

    @Override
    public Order get(Long id) {
        validatorService.validateRequestIdNotNull(id);
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
                order.setDishes(new ArrayList<DishQuantity>());
                order.setClient(parseClient(request.getClient()));
                break;
            case UPDATE:
                order = get(request.getId());
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
        if(validatorService.isEnumValid(request.getStatus(), OrderStatus.class)) {
            order.setStatus(OrderStatus.valueOf(request.getStatus()));
        }
        Order savedOrder = dao.save(order);
        dishQuantityRepository.deleteInBatch(order.getDishes());
        return savedOrder;
    }

    @Override
    public Order declineOrder(OrderModel request) {
        validatorService.validateRequestNotNull(request, Order.class);
        Order order = get(request.getId());
        if(validatorService.isEnumValid(request.getStatus(), OrderStatus.class)) {
            order.setStatus(OrderStatus.valueOf(request.getStatus()));
        }
        order.setMessage(order.getMessage());
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
    private Client parseClient(ClientModel customerData) {
        Client client = clientRepositories.findByPhoneNumber(customerData.getPhone());
        if (client != null) {
            return client;
        } else {
            client = new Client();
            client.setName(customerData.getName());
            client.setAddress(customerData.getAddress());
            client.setPhoneNumber(customerData.getPhone());
            return clientService.save(client);
        }
    }

    private Double countAmount(List<DishQuantityModel> request) {
        Double amount = 0.0;
        for (DishQuantityModel dishPosition : request) {
            amount += dishService.get(dishPosition.getDishId()).getPrice() * dishPosition.getQuantity();
        }
        return amount;
    }
}
