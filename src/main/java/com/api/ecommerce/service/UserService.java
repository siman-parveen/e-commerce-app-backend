package com.api.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.entity.User;
import com.api.ecommerce.repo.UserRepo;
import com.api.ecommerce.response.Response;

@Service
public class UserService {

	private UserRepo repo;
	
	@Autowired
	private CommonService commonService;

	public UserService(UserRepo repo) {
		this.repo = repo;
	}

	public ResponseEntity<?> retrieveAllUsers(){
		Response<List<User>> response = new Response<List<User>>();

		try {
			List<User> users = repo.findAll();
			if(users == null || users.isEmpty()) {
				response.setMessage(CommonConstant.NO_USERS_FOUND);
			} else {
				response.setMessage(CommonConstant.RETRIEVE_ALL_USERS_SUCCESS);
			}
			response.setSuccess(true);
			response.setData(users);
		} catch(Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			return ResponseEntity.internalServerError().body(response);
		}
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> retrieveUserById(Long id) {
		Response<User> response = new Response<User>();
		
		try {
			User user = repo.findById(id).orElseThrow(() -> new RuntimeException(CommonConstant.NO_USER_FOUND));
			response.setMessage(CommonConstant.RETRIVE_USER_SUCCESS);
			response.setSuccess(true);
			response.setData(user);
		}catch (RuntimeException e) {
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
	
	public ResponseEntity<?> addUser(User newUser) {
		
		Response<User> response = new Response<User>();
		try {
			repo.save(newUser);
			response.setMessage(CommonConstant.USER_ADDED_SUCCESS);
			response.setSuccess(true);
			response.setData(newUser);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> updateUserInfo(Long id, User updatedUserInfo) {
		
		Response<User> response = new Response<User>();
		try {
			
			User user = repo.findById(id).orElseThrow(() -> new RuntimeException(CommonConstant.NO_USER_FOUND));
			
			if(commonService.isUpdated(user.getUserName(), updatedUserInfo.getUserName())) {
				user.setUserName(updatedUserInfo.getUserName());
			}
			
			if(commonService.isUpdated(user.getAddress(), updatedUserInfo.getAddress())) {
				user.setAddress(updatedUserInfo.getAddress());
			}
			
			repo.save(user);
			
			response.setMessage(CommonConstant.USER_UPDATE_SUCCESS);
			response.setSuccess(true);
			response.setData(user);
			
		} catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
			
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> removeUser(Long userId) {
		
		
		Response<User> response = new Response<User>();
		try {
			repo.findById(userId).orElseThrow(() -> new RuntimeException(CommonConstant.NO_USER_FOUND));
			repo.deleteById(userId);
			response.setMessage(CommonConstant.USER_DELETE_SUCCESS);
			response.setSuccess(true);
			
		} catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
			
		}catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}
	
	
}
