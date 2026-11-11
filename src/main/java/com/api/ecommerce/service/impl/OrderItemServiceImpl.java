package com.api.ecommerce.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.OrderItem;
import com.api.ecommerce.repo.OrderItemRepo;
import com.api.ecommerce.response.Response;
import com.api.ecommerce.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {
	
	@Autowired
	private OrderItemRepo repo;
	
	@Autowired
	private CommonService commonService;

	public ResponseEntity<?> retrieveOrderItemForOrder(String orderExternalId) {
		Response<OrderItem> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		
		if(order == null) {
			response.setSuccess(false);
			response.setMessage("No order found!!");
			return ResponseEntity.ok().body(response);
		}
		
		Optional<OrderItem> orderItem = repo.findByOrder(order);
		
		if(orderItem.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No order item (with given id) found!!");
			return ResponseEntity.ok().body(response);
		}
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDER_ITEM_RETRIEVED_SUCCESSFULLY);
		response.setResponseData(orderItem.get());
		
		return ResponseEntity.ok().body(response);
	}

}
