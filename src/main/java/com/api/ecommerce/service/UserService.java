package com.api.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.api.ecommerce.dto.request.UserRequestDTO;

public interface UserService {
	
	public ResponseEntity<?> retrieveAllUsers();
	public ResponseEntity<?> retrieveUserById(String userExternalId);
	public ResponseEntity<?> addUser(UserRequestDTO userDto);
	public ResponseEntity<?> updateUserInfo(String userExternalId, UserRequestDTO userDto);
	public ResponseEntity<?> removeUser(String userExternalId);
	public ResponseEntity<?> retrieveByEmailAndPassword(UserRequestDTO userDto);

}
