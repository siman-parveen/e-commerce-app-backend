package com.api.ecommerce.service;

import com.api.ecommerce.response.Response;


import com.api.ecommerce.dto.request.UserRequestDTO;

public interface UserService {
	
	public Response<?> retrieveAllUsers();
	public Response<?> retrieveUserById(String userExternalId);
	public Response<?> addUser(UserRequestDTO userDto);
	public Response<?> updateUserInfo(String userExternalId, UserRequestDTO userDto);
	public Response<?> removeUser(String userExternalId);
	public Response<?> retrieveByEmailAndPassword(UserRequestDTO userDto);

}
