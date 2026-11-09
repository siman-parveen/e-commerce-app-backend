package com.api.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.dto.request.UserRequestDTO;
import com.api.ecommerce.dto.response.UserResponseDTO;
import com.api.ecommerce.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name = "User API", description = "Operations related to E-commerce user")
public class UserRestController {

	@Autowired
	private UserService service;
	
	@Operation(summary = "Retrieve All Users", description = "API returns all the registered users")
	@GetMapping("/admin/users")
	public ResponseEntity<?> retrieveAllUsers() {
		return service.retrieveAllUsers();
	}
	
	@Operation(summary = "Retrieve User by ID", description = "API returns the registered user by the External ID")
	@GetMapping("/admin/user/{userExternalId}")
	public ResponseEntity<?> retrieveUserById(@PathVariable String userExternalId) {
		return service.retrieveUserById(userExternalId);
	}
	
	@Operation(summary = "Retrieve User by EmailID & Password", description = "API returns the registered user by the Email Id and Password")
	@PostMapping("/user/login")
	public ResponseEntity<?> retrieveByEmailAndPassword(@Valid @RequestBody UserRequestDTO user) {
		return service.retrieveByEmailAndPassword(user);
	}
	
	@Operation(summary = "Register a new user", description = "API registers the user and returns the same user")
	@PostMapping("/user/signup")
	public ResponseEntity<?> addUser(@Valid @RequestBody UserRequestDTO user) {
		return service.addUser(user);
	}
	
	@Operation(summary = "Updates a registered user", description = "API updates the information for the user and returns the updated registered user")
	@PatchMapping("/user/{userExternalId}")
	public ResponseEntity<?> updateUserInfo(@PathVariable String userExternalId, @Valid @RequestBody UserRequestDTO user) {
		return service.updateUserInfo(userExternalId, user);
	}
	
	@Operation(summary = "De-register a user by ID", description = "API deletes the user and returns a success delete message")
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<?> removeUser(@PathVariable String userExternalId){
		return service.removeUser(userExternalId);
	}
	
}
