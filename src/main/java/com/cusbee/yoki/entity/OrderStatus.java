package com.cusbee.yoki.entity;

public enum OrderStatus {

	FRESH, 			//new order from website
	IN_PROGRESS, 	//order is processing by call-center
	DECLINED, 		//order was rejected during call-center processing
	KITCHEN, 		//order was sent to kitchen
	CANT_PREPARE,	//kitchen could not prepare order
	COOKING,		//order is being prepared
	DELIVERY,		//order is on its way to consumer
	DONE, 			//order was successfully delivered
	CLOSED			//call-center had done a control call and closed the order
}
