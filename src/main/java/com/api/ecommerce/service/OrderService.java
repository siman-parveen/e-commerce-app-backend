package com.api.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.User;
import com.api.ecommerce.repo.OrderRepo;
import com.api.ecommerce.repo.UserRepo;
import com.api.ecommerce.response.Response;

@Service
public class OrderService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private OrderRepo orderRepo;

	public ResponseEntity<?> retrieveOrdersForUser(Long userId) {
		Response<List<Order>> response = new Response<>();
		
		try {
			User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException(CommonConstant.NO_USER_FOUND));

			List<Order> orders = orderRepo.findByUser(user).orElseThrow(() -> new RuntimeException("No orders found for the user!!"));
			response.setSuccess(true);
			response.setMessage(CommonConstant.ORDERS_RETRIEVED_SUCCESS_FOR_USER);
			response.setData(orders);
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

	public ResponseEntity<?> retrieveOrder(Long orderId) {
		Response<Order> response = new Response<>();
		
		try {
			Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("No order (with given id) found for the user!!"));
			response.setSuccess(true);
			response.setMessage(CommonConstant.ORDER_RETRIEVED_SUCCESS_FOR_USER);
			response.setData(order);
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

	public ResponseEntity<?> createOrderForUser(Long userId, Order order) {
		Response<Order> response = new Response<>();
		
		try {
			User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException(CommonConstant.NO_USER_FOUND));
			
			order.setUser(user);
			//need to add order item
			
			orderRepo.save(order);
			
			response.setSuccess(true);
			response.setMessage(CommonConstant.ORDER_CREATED_SUCCESS_FOR_USER);
			response.setData(order);
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

	public ResponseEntity<?> removeOrder(Long orderId) {
		Response<Order> response = new Response<>();
		
		try {
			orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("No order (with given id) found for the user!!"));
			orderRepo.deleteById(orderId);
			response.setSuccess(true);
			response.setMessage(CommonConstant.ORDER_DELETED_SUCCESS_FOR_USER);
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
