package com.api.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.UserRequestDTO;
import com.api.ecommerce.dto.response.UserResponseDTO;
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
		Response<List<UserResponseDTO>> response = new Response<List<UserResponseDTO>>();

		try {
			List<User> users = repo.findAll();
			List<UserResponseDTO> dtoList = new ArrayList<UserResponseDTO>();

			users.stream().forEach(user -> {
				UserResponseDTO dto = commonService.convertUsersToUserResponseDTO(user);
				dtoList.add(dto);
			});
			
			if(users == null || users.isEmpty()) {
				response.setMessage(CommonConstant.NO_USERS_FOUND);
			} else {
				response.setMessage(CommonConstant.RETRIEVE_ALL_USERS_SUCCESS);
			}
			response.setSuccess(true);
			response.setData(dtoList);
		} catch(Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			response.setErrorTrace(e.getStackTrace().toString());
			return ResponseEntity.internalServerError().body(response);
		}
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> retrieveUserById(String userExternalId) {
		Response<UserResponseDTO> response = new Response<UserResponseDTO>();
		
		try {
			User user = commonService.findUserByExternalId(userExternalId);
			UserResponseDTO dto = commonService.convertUsersToUserResponseDTO(user);
			response.setMessage(CommonConstant.RETRIVE_USER_SUCCESS);
			response.setSuccess(true);
			response.setData(dto);
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
	
	public ResponseEntity<?> addUser(UserRequestDTO userDto) {
		
		Response<UserResponseDTO> response = new Response<UserResponseDTO>();
		try {
			
			repo.findByEmailIdOrPhoneNumber(userDto.getEmailId(), userDto.getPhoneNumber()).orElseThrow(() -> new RuntimeException("Email ID/Phone Number Already registered"));
			
			User user = commonService.convertUserRequestDTOToUser(userDto);
			repo.save(user);
			UserResponseDTO dto = commonService.convertUsersToUserResponseDTO(user);
			response.setMessage(CommonConstant.USER_ADDED_SUCCESS);
			response.setSuccess(true);
			response.setData(dto);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			response.setErrorTrace(e.getStackTrace().toString());
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> updateUserInfo(String userExternalId, UserRequestDTO userDto) {
		
		Response<UserResponseDTO> response = new Response<UserResponseDTO>();
		try {
			
			User user = commonService.findUserByExternalId(userExternalId);
			
			if(userDto.getUserName() != null && commonService.isUpdated(user.getUserName(), userDto.getUserName())) {
				user.setUserName(userDto.getUserName());
			}
			
//			if(userDto.getAddress() != null && commonService.isUpdated(user.getAddress(), userDto.getAddress())) {
//				user.setAddress(user.getAddress().add(userDto.getAddress()));
//			}
			
			repo.save(user);
			
			UserResponseDTO dto = commonService.convertUsersToUserResponseDTO(user);
			
			response.setMessage(CommonConstant.USER_UPDATE_SUCCESS);
			response.setSuccess(true);
			response.setData(dto);
			
		} catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
			
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			response.setErrorTrace(e.getStackTrace().toString());
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> removeUser(String userExternalId) {
		
		Response<?> response = new Response<>();
		try {
			User user = commonService.findUserByExternalId(userExternalId);
			repo.deleteById(user.getEntityId());
			response.setMessage(CommonConstant.USER_DELETE_SUCCESS);
			response.setSuccess(true);
			
		} catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
			
		}catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			response.setErrorTrace(e.getStackTrace().toString());
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}
	
	
}
