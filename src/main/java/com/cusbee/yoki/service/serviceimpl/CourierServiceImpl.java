package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.dao.CourierDao;
import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.repositories.AccountRepository;
import com.cusbee.yoki.service.ActivationService;
import com.cusbee.yoki.service.CourierService;
import com.cusbee.yoki.service.OrderService;
import com.cusbee.yoki.service.ValidatorService;

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
public class CourierServiceImpl implements CourierService {

    @Autowired
    CourierDao dao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ValidatorService validatorService;

    @Autowired
    ActivationService activationService;

    @Autowired
    OrderService orderService;

    @Autowired
    CourierService courierService;

    @Override
    public Courier get(Long id) {
        validatorService.validateRequestIdNotNull(id, Courier.class);
        Courier courier = accountRepository.findCourierById(id);
        validatorService.validateEntityNotNull(courier, Courier.class);
        return courier;
    }

    @Override
    public List<Courier> getAll() {
        return dao.getAll();
    }

    @Override
    public void remove(Long id) {
        //I think we should not ever remove couriers. Instead we could deactivate them.
    }

    //we can update status, but enable/disable we should do in account
    @Override
    public Courier updateStatus(Long id, Courier.CourierStatus status) {
        Courier courier = get(id);
        courier.setStatus(status);
        return dao.save(courier);
    }

    @Override
	public List<Courier> getAllAvailableCouriers() {
		return dao.getAllAvailableCouriers();
	}

    @Override
    public Order orderDelivered(Long orderId) {
        Order order = orderService.get(orderId);
        Courier courier = order.getCourier();
        order.setStatus(OrderStatus.DONE);
        order.setTimeDelivered(Calendar.getInstance());
        courierService.updateStatus(courier.getId(), Courier.CourierStatus.FREE);
        return orderDao.save(order);
    }
}
