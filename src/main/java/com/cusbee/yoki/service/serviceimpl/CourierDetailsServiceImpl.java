package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.dao.CourierDao;
import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.CourierModel;
import com.cusbee.yoki.repositories.CourierRepository;
import com.cusbee.yoki.service.CourierDetailsService;
import com.cusbee.yoki.service.OrderService;
import com.cusbee.yoki.service.ValidatorService;

import com.cusbee.yoki.utils.ErrorCodes;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * Дима, шо надо сделать:
 * 1. При создании курьера надо чтобы одновременно создавался аккаунт, у которого будет
 * authority - COURIER, плюс параллельно в базе создавалась запись курьера (таблица courier),
 * где одна из колонок - id аакаунта. Я частично попробовал прописать логику, но хз как оно будет
 * работать с базой.
 * 2. Нужно прописать адекватное сохранение/изменение такого курьер-аккаунта.
 * 3. Нужно прописать корректное вынимание цельного курьера из базы (get). Для этого вероятно придется
 * подкорректировать как entity, так и model.
 * 4. Удаление аккаунта курьера недопустимо. Его не надо удалять. Надо прописать активацию-деактивацию так,
 * чтобы мы могли достать из базы курьера, найти id его аккаунта и использовать для деактивации уже готовый
 * метод в accountService.
 */
@Service
public class CourierDetailsServiceImpl implements CourierDetailsService {

    @Autowired
    CourierDao dao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    ValidatorService validatorService;

    @Autowired
    OrderService orderService;

    @Autowired
    CourierDetailsService courierService;

    @Autowired
    CourierRepository courierRepository;

    @Override
    public CourierDetails get(Long id) {
        validatorService.validateRequestIdNotNull(id, CourierDetails.class);
        CourierDetails courierDetails = dao.get(id);
        validatorService.validateEntityNotNull(courierDetails, CourierDetails.class);
        return courierDetails;
    }

    @Override
    public CourierDetails getCourierByUsername(String username) {
        if(StringUtils.isNotEmpty(username)) {
            CourierDetails courierDetails = courierRepository.getCourierDetailsByUsername(username);
            validatorService.validateEntityNotNull(courierDetails, CourierDetails.class);
            return courierDetails;
        } else {
            throw new ApplicationException(ErrorCodes.User.INVALID_USERNAME,
                    "Username should not be empty!");
        }
    }

    @Override
    public List<CourierDetails> getAllCouriers() {
        return dao.getAllCouriers();
    }

    @Override
    public List<CourierDetails> getAvailableCouriers() {
        return dao.getAvailableCouriers();
    }

    @Override
    public void remove(Long id) {
        //I think we should not ever remove couriers. Instead we could deactivate them.
    }

    //we can update status, but enable/disable we should do in account
    @Override
    public CourierDetails updateStatus(Long id, CourierDetails.CourierStatus status) {
        CourierDetails courierDetails = get(id);
        courierDetails.setStatus(status);
        return dao.save(courierDetails);
    }

    @Override
    public Order orderDelivered(Long orderId) {
        Order order = orderService.get(orderId);
        CourierDetails courierDetails = order.getCourierDetails();
        order.setStatus(OrderStatus.DONE);
        order.setTimeDelivered(Calendar.getInstance());
        courierService.updateStatus(courierDetails.getId(), CourierDetails.CourierStatus.FREE);
        return orderDao.save(order);
    }

    @Override
    public CourierDetails saveCourierDetails(CourierModel request) {
        if(StringUtils.isNotEmpty(request.getMessagingToken())) {
            CourierDetails courierDetails = get(request.getId());
            courierDetails.setMessagingToken(request.getMessagingToken());
            return dao.save(courierDetails);
        } else {
            throw new ApplicationException(ErrorCodes.Courier.INVALID_TOKEN,
                    "Messaging token should not be empty!");
        }
    }
}
