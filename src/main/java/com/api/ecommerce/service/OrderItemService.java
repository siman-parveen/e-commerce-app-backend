package com.api.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.OrderItem;
import com.api.ecommerce.exception.ApiException;
import com.api.ecommerce.repo.OrderItemRepo;
import com.api.ecommerce.response.Response;

@Service
public class OrderItemService {
	
	@Autowired
	private OrderItemRepo repo;
	
	@Autowired
	private CommonService commonService;

	public ResponseEntity<?> retrieveOrderItemForOrder(String orderExternalId) {
		Response<OrderItem> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		OrderItem orderItem = repo.findByOrder(order).orElseThrow(() -> new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,"No order item (with given id) found!!"));
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDER_ITEM_RETRIEVED_SUCCESSFULLY);
		response.setData(orderItem);
		
		return ResponseEntity.ok().body(response);
	}

}
