package com.api.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.OrderItem;
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
		
		try {
			Order order = commonService.findOrderByExternalId(orderExternalId);
			OrderItem orderItem = repo.findByOrder(order).orElseThrow(() -> new RuntimeException("No order item (with given id) found!!"));
			response.setSuccess(true);
			response.setMessage(CommonConstant.ORDER_ITEM_RETRIEVED_SUCCESSFULLY);
			response.setData(orderItem);
		} catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			response.setErrorTrace(e.getStackTrace().toString());
			return ResponseEntity.internalServerError().body(response);
		}
		return ResponseEntity.ok().body(response);
	}

}
