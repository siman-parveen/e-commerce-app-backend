package com.api.ecommerce.controller;

import com.api.ecommerce.response.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.api.ecommerce.dto.request.UserRequestDTO;
import com.api.ecommerce.dto.response.AuthSessionDTO;
import com.api.ecommerce.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api")
@Tag(name = "User API", description = "Operations related to E-commerce user")
public class UserRestController {

	@Autowired
	private UserService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private SecurityContextRepository securityContextRepository;
	
	@Operation(summary = "Retrieve All Users", description = "API returns all the registered users")
	@GetMapping("/admin/users")
	public ResponseEntity<?> retrieveAllUsers() {
		Response<?> response = service.retrieveAllUsers();
		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "Retrieve User by ID for admin")
	@GetMapping("/admin/users/{userExternalId}")
	public ResponseEntity<?> retrieveUserForAdmin(@PathVariable String userExternalId) {
		Response<?> response = service.retrieveUserById(userExternalId);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Retrieve User by ID", description = "API returns the registered user by the External ID")
	@GetMapping("/user/{userExternalId}")
	@PreAuthorize("@userAuthorization.isCurrentUser(#p0)")
	public ResponseEntity<?> retrieveUserById(@PathVariable String userExternalId) {
		Response<?> response = service.retrieveUserById(userExternalId);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Retrieve User by EmailID & Password", description = "API returns the registered user by the Email Id and Password")
	@PostMapping("/user/login")
	public ResponseEntity<?> retrieveByEmailAndPassword(
			@Valid @RequestBody UserRequestDTO user,
			HttpServletRequest request,
			HttpServletResponse servletResponse) {
		if (user.getEmailId() == null || user.getEmailId().isBlank()) {
			return authenticationFailure();
		}
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getEmailId(), user.getPassword()));
			saveAuthentication(authentication, request, servletResponse);
			AuthSessionDTO session = toSessionDTO(authentication);
			Response<AuthSessionDTO> response = new Response<>();
			response.setSuccess(true);
			response.setMessage("Login successful");
			response.setResponseData(session);
			return ResponseEntity.ok(response);
		} catch (AuthenticationException exception) {
			return authenticationFailure();
		}
	}

	@GetMapping("/auth/session")
	public ResponseEntity<?> retrieveCurrentSession(Authentication authentication) {
		Response<AuthSessionDTO> response = new Response<>();
		response.setSuccess(true);
		response.setMessage("Session retrieved");
		response.setResponseData(toSessionDTO(authentication));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/auth/csrf")
	public ResponseEntity<?> retrieveCsrfToken(CsrfToken csrfToken) {
		return ResponseEntity.ok(java.util.Map.of("token", csrfToken.getToken()));
	}
	
	@Operation(summary = "Register a new user", description = "API registers the user and returns the same user")
	@PostMapping("/user/signup")
	public ResponseEntity<?> addUser(
			@Valid @RequestBody UserRequestDTO user,
			HttpServletRequest request,
			HttpServletResponse servletResponse) {
		if (user.getEmailId() == null || user.getEmailId().isBlank()) {
			Response<?> response = new Response<>();
			response.setSuccess(false);
			response.setMessage("Email ID is required");
			return ResponseEntity.badRequest().body(response);
		}
		Response<?> response = service.addUser(user);
		if (response.isSuccess()) {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getEmailId(), user.getPassword()));
			saveAuthentication(authentication, request, servletResponse);
		}
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Updates a registered user", description = "API updates the information for the user and returns the updated registered user")
	@PatchMapping("/user/{userExternalId}")
	@PreAuthorize("@userAuthorization.isCurrentUser(#p0)")
	public ResponseEntity<?> updateUserInfo(@PathVariable String userExternalId, @Valid @RequestBody UserRequestDTO user) {
		Response<?> response = service.updateUserInfo(userExternalId, user);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "De-register a user by ID", description = "API deletes the user and returns a success delete message")
	@DeleteMapping("/user/{userExternalId}")
	@PreAuthorize("@userAuthorization.isCurrentUser(#p0)")
	public ResponseEntity<?> removeUser(@PathVariable String userExternalId){
		Response<?> response = service.removeUser(userExternalId);
		return ResponseEntity.ok().body(response);
	}

	private void saveAuthentication(
			Authentication authentication,
			HttpServletRequest request,
			HttpServletResponse response) {
		if (request.getSession(false) != null) {
			request.changeSessionId();
		}
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
		securityContextRepository.saveContext(context, request, response);
	}

	private AuthSessionDTO toSessionDTO(Authentication authentication) {
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		return new AuthSessionDTO(isAdmin ? null : authentication.getName(), isAdmin ? "ADMIN" : "USER");
	}

	private ResponseEntity<?> authenticationFailure() {
		Response<?> response = new Response<>();
		response.setSuccess(false);
		response.setMessage("Email ID/Password is incorrect");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
	
}
