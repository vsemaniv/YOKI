package com.cusbee.yoki.service.serviceimpl;

import java.util.*;

import com.cusbee.yoki.entity.*;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.model.ClientModel;
import com.cusbee.yoki.repositories.OrderRepository;
import com.cusbee.yoki.service.*;

import com.cusbee.yoki.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.DishQuantityModel;
import com.cusbee.yoki.model.OrderModel;

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
    private MessagingService messagingService;

    @Autowired
    private CourierDetailsService courierService;

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
        return repository.getAvailableOrders();
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
            throw new ApplicationException(HttpStatus.UNPROCESSABLE_ENTITY,
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
                order.setClient(parseClient(request.getClient(), new Client()));
                break;
            case UPDATE:
                order = get(request.getId());
                order.setClient(parseClient(request.getClient(), clientService.get(request.getClient().getPhone())));
                if(request.getCourierId() != null) {
                    order.setCourierDetails(courierService.get(request.getCourierId()));
                }
                if(request.getTimeTaken() != null){
                    order.setTimeTaken(DateUtil.getCalendar(request.getTimeTaken()));
                }
                if(request.getTimeDelivered() != null){
                	order.setTimeDelivered(DateUtil.getCalendar(request.getTimeDelivered()));
                }
                break;
            default:
                throw new ApplicationException(HttpStatus.BAD_REQUEST,
                        "Invalid Request");
        }
        if(validatorService.isEnumValid(request.getStatus(), OrderStatus.class)) {
            order.setStatus(OrderStatus.valueOf(request.getStatus()));
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
            throw new ApplicationException(HttpStatus.BAD_REQUEST,
                    "Invalid order status");
        }
        if(StringUtils.isNotEmpty(request.getMessage())) {
            order.setMessage(request.getMessage());
        } else {
            throw new ApplicationException(HttpStatus.BAD_REQUEST,
                    "Decline message should not be empty!");
        }
        releaseCourierIfExist(order);
        return dao.save(order);
    }

    @Override
    public Order assignCourierToOrder(OrderModel request) {
        validatorService.validateRequestNotNull(request, Order.class);
        Order order = get(request.getId());
        Calendar timeToTake = DateUtil.getCalendar(request.getTimeToTake());
        Calendar timeToDeliver = DateUtil.getCalendar(request.getTimeToDeliver());
        releaseCourierIfExist(order);
        CourierDetails courier = courierService.get(request.getCourierId());
        courier.setStatus(CourierDetails.CourierStatus.BUSY);
        if(timeToTake != null && timeToDeliver != null){
            order.setTimeToTake(timeToTake);
            order.setTimeToDeliver(timeToDeliver);
            order.setCourierDetails(courier);
            dao.save(order);
            messagingService.notifyCourier(courier, order);
            return order;
        } else {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Assigned time to take order and time to deliver order should not be empty");
        }
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

    private void releaseCourierIfExist(Order order) {
        CourierDetails currentCourier = order.getCourierDetails();
        if(currentCourier != null && currentCourier.getStatus() != CourierDetails.CourierStatus.OUT) {
            courierService.updateStatus(currentCourier.getId(), CourierDetails.CourierStatus.FREE);
        }
        messagingService.releaseCourier(currentCourier, order);
    }
}
