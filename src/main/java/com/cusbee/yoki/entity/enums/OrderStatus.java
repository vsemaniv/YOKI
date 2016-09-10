package com.cusbee.yoki.entity.enums;

public enum OrderStatus {

	FRESH, 			//new order from website
	IN_PROGRESS, 	//order is processing by call-center
	DECLINED, 		//order was rejected during call-center processing
	KITCHEN, 		//order was sent to kitchen
	CANT_PREPARE,	//kitchen could not prepare order
	COOKING,		//order is being prepared
	PREPARED,		//order is ready to deliver
	DELIVERY,		//order is on its way to consumer
	DELIVERY_FAILED,//failed to deliver order
	DONE, 			//order was successfully delivered
}
