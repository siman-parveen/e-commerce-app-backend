package com.api.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.api.ecommerce.dto.response.OrderItemResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

	public Response<?> retrieveOrderItemForOrder(String orderExternalId) {
		Response<List<OrderItemResponseDTO>> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		
		if(order == null) {
			response.setSuccess(false);
			response.setMessage("No order found!!");
			return response;
		}
		
		Optional<List<OrderItem>> orderItem = repo.findByOrder(order);
		
		if(orderItem.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No order items (with given id) found!!");
			return response;
		}

		List<OrderItemResponseDTO> orderItemDtos = new ArrayList<>();

		List<OrderItem> orderItems = orderItem.get();
		orderItems.forEach(item -> orderItemDtos.add(commonService.convertOrderItemToOrderItemResponseDTO(item)));
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDER_ITEM_RETRIEVED_SUCCESSFULLY);
		response.setResponseData(orderItemDtos);
		
		return response;
	}

}
