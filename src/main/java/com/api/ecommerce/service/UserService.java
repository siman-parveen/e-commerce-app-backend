package com.api.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.UserRequestDTO;
import com.api.ecommerce.dto.response.UserResponseDTO;
import com.api.ecommerce.entity.Cart;
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

		List<User> users = repo.findAll();
		
		if(users == null || users.isEmpty()) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_USERS_FOUND);
			return ResponseEntity.ok().body(response);
		}
		
		List<UserResponseDTO> dtoList = users.stream().map(user -> commonService.convertUsersToUserResponseDTO(user)).toList();
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.RETRIEVE_ALL_USERS_SUCCESS);
		response.setData(dtoList);
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> retrieveUserById(String userExternalId) {
		Response<UserResponseDTO> response = new Response<UserResponseDTO>();
		
		User user = commonService.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_USER_FOUND);
			return ResponseEntity.ok().body(response);
		}
		
		UserResponseDTO dto = commonService.convertUsersToUserResponseDTO(user);
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.RETRIVE_USER_SUCCESS);
		response.setData(dto);
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> addUser(UserRequestDTO userDto) {
		
		Response<UserResponseDTO> response = new Response<UserResponseDTO>();

		if(repo.findByEmailIdOrPhoneNumber(userDto.getEmailId(), userDto.getPhoneNumber()).isPresent()) {
			response.setSuccess(false);
			response.setMessage("Email ID/Phone Number Already registered");
			return ResponseEntity.ok().body(response);
		}
		
		User user = commonService.convertUserRequestDTOToUser(userDto);
		
		Cart cart = new Cart();
		cart.setExternalId(commonService.generateExternalIdForEntities("CRT"));
		cart.setUser(user);
		
		user.setCart(cart);
		
		UserResponseDTO dto = commonService.convertUsersToUserResponseDTO(user);

		repo.save(user);
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.USER_ADDED_SUCCESS);
		response.setData(dto);
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> updateUserInfo(String userExternalId, UserRequestDTO userDto) {
		
		Response<UserResponseDTO> response = new Response<UserResponseDTO>();
			
		User user = commonService.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_USER_FOUND);
			return ResponseEntity.ok().body(response);
		}
		
		if(userDto.getUserName() != null && commonService.isUpdated(user.getUserName(), userDto.getUserName())) {
			user.setUserName(userDto.getUserName());
		}
		
		if(userDto.getEmailId() != null && commonService.isUpdated(user.getEmailId(), userDto.getEmailId())) {
			user.setEmailId(userDto.getEmailId());
		}
		
		if(userDto.getPhoneNumber() != null && commonService.isUpdated(user.getPhoneNumber(), userDto.getPhoneNumber())) {
			user.setPhoneNumber(userDto.getPhoneNumber());
		}
		
		repo.save(user);
		
		UserResponseDTO dto = commonService.convertUsersToUserResponseDTO(user);
		
		response.setMessage(CommonConstant.USER_UPDATE_SUCCESS);
		response.setSuccess(true);
		response.setData(dto);
			
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> removeUser(String userExternalId) {
		
		Response<?> response = new Response<>();

		User user = commonService.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_USER_FOUND);
			return ResponseEntity.ok().body(response);
		}
		
		repo.deleteById(user.getEntityId());
		response.setMessage(CommonConstant.USER_DELETE_SUCCESS);
		response.setSuccess(true);
			
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> retrieveByEmailIDandPassword(UserRequestDTO userDto) {
		Response<UserResponseDTO> response = new Response<UserResponseDTO>();

		Optional<User> user = repo.findByEmailIdAndPassword(userDto.getEmailId(), userDto.getPassword());
		
		if(user == null || user.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("Email ID/ Password is incorrect");
			return ResponseEntity.ok().body(response);
		}
		
		UserResponseDTO dto = commonService.convertUsersToUserResponseDTO(user.get());
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.RETRIVE_USER_SUCCESS);
		response.setData(dto);
		
		return ResponseEntity.ok().body(response);
	}
	
	
}
