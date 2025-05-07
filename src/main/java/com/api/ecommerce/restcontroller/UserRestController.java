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

import com.api.ecommerce.dto.request.UserRequestDTO;
import com.api.ecommerce.dto.response.UserResponseDTO;
import com.api.ecommerce.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User API", description = "Operations related to E-commerce user")
public class UserRestController {

	@Autowired
	private UserService service;
	
	@Operation(summary = "Retrieve All Users", description = "API returns all the registered users")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Users retrieved successfully!!", content = @Content(mediaType = "/application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@GetMapping("/all")
	public ResponseEntity<?> retrieveAllUsers() {
		return service.retrieveAllUsers();
	}
	
	///
	
	@Operation(summary = "Retrieve User by ID", description = "API returns the registered user by the External ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "User with given id retrieved successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = UserResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@GetMapping("/{userExternalId}")
	public ResponseEntity<?> retrieveUserById(@PathVariable String userExternalId) {
		return service.retrieveUserById(userExternalId);
	}
	
	///
	
	@Operation(summary = "Register a new user", description = "API registers the user and returns the same user")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Users added successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = UserResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@PostMapping
	public ResponseEntity<?> addUser(@Valid @RequestBody UserRequestDTO user) {
		return service.addUser(user);
	}
	
	///
	
	@Operation(summary = "Updates a registered user", description = "API updates the information for the user and returns the updated registered user")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Users updated successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = UserResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@PatchMapping("/{userExternalId}")
	public ResponseEntity<?> updateUserInfo(@PathVariable String userExternalId, @Valid @RequestBody UserRequestDTO user) {
		return service.updateUserInfo(userExternalId, user);
	}
	
	///
	
	@Operation(summary = "De-register a user by ID", description = "API deletes the user and returns a success delete message")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Users deleted successfully!!", content = @Content(mediaType = "/application/json")),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> removeUser(@PathVariable String userExternalId){
		return service.removeUser(userExternalId);
	}
	
}
