package com.api.ecommerce.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.api.ecommerce.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.UserRequestDTO;
import com.api.ecommerce.dto.response.UserResponseDTO;
import com.api.ecommerce.entity.Cart;
import com.api.ecommerce.entity.User;
import com.api.ecommerce.repo.UserRepo;
import com.api.ecommerce.response.Response;
import com.api.ecommerce.service.UserService;
import org.springframework.util.CollectionUtils;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepo repo;
	
	@Autowired
	private CommonService commonService;

	public UserServiceImpl(UserRepo repo) {
		this.repo = repo;
	}

	public Response<?> retrieveAllUsers(){
		Response<List<UserResponseDTO>> response = new Response<>();

		List<User> users = repo.findAll();
		
		if(CollectionUtils.isEmpty(users)) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_USERS_FOUND);
			return response;
		}
		
		List<UserResponseDTO> dtoList = users.stream().map(user -> commonService.convertUsersToUserResponseDTO(user)).toList();
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.RETRIEVE_ALL_USERS_SUCCESS);
		response.setResponseData(dtoList);
		return response;
	}

	public Response<?> retrieveUserById(String userExternalId) {
		Response<UserResponseDTO> response = new Response<>();
		
		User user = commonService.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_USER_FOUND);
			return response;
		}
		
		UserResponseDTO dto = commonService.convertUsersToUserResponseDTO(user);
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.RETRIEVE_USER_SUCCESS);
		response.setResponseData(dto);
		
		return response;
	}
	
	public Response<?> addUser(UserRequestDTO userDto) {
		
		Response<UserResponseDTO> response = new Response<>();

		if(repo.findByEmailIdOrPhoneNumber(userDto.getEmailId(), userDto.getPhoneNumber()).isPresent()) {
			response.setSuccess(false);
			response.setMessage("Email ID/Phone Number Already registered");
			return response;
		}
		
		User user = commonService.convertUserRequestDTOToUser(userDto);
		
		Cart cart = new Cart();
		cart.setExternalId(commonService.generateExternalIdForEntities("CRT"));
		cart.setUser(user);

		Wallet wallet = new Wallet();
		wallet.setExternalId(commonService.generateExternalIdForEntities("WLT"));
		wallet.setAmount(BigDecimal.ZERO);
		wallet.setUser(user);
		
		user.setCart(cart);
		user.setWallet(wallet);
		
		UserResponseDTO dto = commonService.convertUsersToUserResponseDTO(user);

		repo.save(user);
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.USER_ADDED_SUCCESS);
		response.setResponseData(dto);
		
		return response;
	}
	
	public Response<?> updateUserInfo(String userExternalId, UserRequestDTO userDto) {
		
		Response<UserResponseDTO> response = new Response<>();
			
		User user = commonService.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_USER_FOUND);
			return response;
		}
		
		if(userDto.getUserName() != null && commonService.isUpdated(user.getUserName(), userDto.getUserName())) {
			user.setUserName(userDto.getUserName());
		}
		
		if(userDto.getEmailId() != null && commonService.isUpdated(user.getEmailId(), userDto.getEmailId())) {
			Optional<User> existingUserWithEmail = repo.findByEmailId(userDto.getEmailId());
			if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getEntityId().equals(user.getEntityId())) {
				response.setSuccess(false);
				response.setMessage("Email ID already registered by another user");
				return response;
			}
			user.setEmailId(userDto.getEmailId());
		}
		
		if(userDto.getPhoneNumber() != null && commonService.isUpdated(user.getPhoneNumber(), userDto.getPhoneNumber())) {
			Optional<User> existingUserWithPhone = repo.findByPhoneNumber(userDto.getPhoneNumber());
			if (existingUserWithPhone.isPresent() && !existingUserWithPhone.get().getEntityId().equals(user.getEntityId())) {
				response.setSuccess(false);
				response.setMessage("Phone Number already registered by another user");
				return response;
			}
			user.setPhoneNumber(userDto.getPhoneNumber());
		}
		
		repo.save(user);
		
		UserResponseDTO dto = commonService.convertUsersToUserResponseDTO(user);
		
		response.setMessage(CommonConstant.USER_UPDATE_SUCCESS);
		response.setSuccess(true);
		response.setResponseData(dto);
			
		return response;
	}
	
	public Response<?> removeUser(String userExternalId) {
		
		Response<?> response = new Response<>();

		User user = commonService.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage(CommonConstant.NO_USER_FOUND);
			return response;
		}
		
		repo.deleteById(user.getEntityId());
		response.setMessage(CommonConstant.USER_DELETE_SUCCESS);
		response.setSuccess(true);
			
		return response;
	}

	public Response<?> retrieveByEmailAndPassword(UserRequestDTO userDto) {
		Response<UserResponseDTO> response = new Response<>();

		Optional<User> user = repo.findByEmailId(userDto.getEmailId());
		
		if(user.isEmpty() || !commonService.passwordMatches(userDto.getPassword(), user.get().getPassword())) {
			response.setSuccess(false);
			response.setMessage("Email ID/ Password is incorrect");
			return response;
		}
		
		UserResponseDTO dto = commonService.convertUsersToUserResponseDTO(user.get());
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.RETRIEVE_USER_SUCCESS);
		response.setResponseData(dto);
		
		return response;
	}
	
	
}
