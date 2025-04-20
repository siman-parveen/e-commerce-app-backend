package com.api.ecommerce.restcontroller;

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

import com.api.ecommerce.entity.User;
import com.api.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity<?> retrieveAllUsers() {
		return service.retrieveAllUsers();
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> retrieveUserById(@PathVariable Long userId) {
		return service.retrieveUserById(userId);
	}
	
	@PostMapping
	public ResponseEntity<?> addUser(@RequestBody User user) {
		return service.addUser(user);
	}
	
	@PatchMapping("/{userId}")
	public ResponseEntity<?> updateUserInfo(@PathVariable Long userId, @RequestBody User user) {
		return service.updateUserInfo(userId, user);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> removeUser(@PathVariable Long userId){
		return service.removeUser(userId);
	}
	
}
