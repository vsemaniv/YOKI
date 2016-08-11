package com.cusbee.yoki.service.serviceimpl;


import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.service.StorageService;
import com.cusbee.yoki.utils.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.service.AdministratorService;

@Service
public class AdministratorServiceImpl implements AdministratorService {

	@Autowired
	private OrderDao dao;

	@Autowired
	private StorageService posterService;
	
	@Override
	public void processIncomingKitchenOrder(Long id, boolean accept) {
    	Order order = dao.get(id);
		if(accept) {
			if(!posterService.writeOffOrder(order)) {
				throw new ApplicationException(ErrorCodes.Order.ERROR_DURING_WRITEOFF,
						"Unexpected error during writeoff. Please contact CRM vendor");
			}
			order.setStatus(OrderStatus.COOKING);
		} else {
			order.setStatus(OrderStatus.CANT_PREPARE);
		}
    	dao.save(order);
    }

}
