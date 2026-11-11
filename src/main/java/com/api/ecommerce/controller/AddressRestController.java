package com.api.ecommerce.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
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

import com.api.ecommerce.dto.request.AddressRequestDTO;
import com.api.ecommerce.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name = "Address API", description = "Operations related to Address of the registered user")
public class AddressRestController {
	
	@Autowired
	private AddressService service;
	
	@Operation(summary = "Retrieve addresses for a user", description = "Returns the saved addresses associated with the specified user.")
	@GetMapping("/user/{userExternalId}/address")
	public ResponseEntity<?> retrieveAddressForUser(@PathVariable String userExternalId) {
		return service.retrieveAddressForUser(userExternalId);
	}
	
	@Operation(summary = "Retrieve addresses for a user", description = "Returns the saved addresses associated with the specified user.")
	@PostMapping("/user/address")
	public ResponseEntity<?> addUserAddress(@Valid @RequestBody AddressRequestDTO addressDto) {
		return service.addUserAddress(addressDto);
	}
	
	@Operation(summary = "Update a saved address", description = "Updates the address identified by its external ID using the supplied details.")
	@PatchMapping("/user/address/{addressExternalId}")
	public ResponseEntity<?> updateUserAddress(@PathVariable String addressExternalId, @Valid @RequestBody AddressRequestDTO addressDto) {
		return service.updateUserAddress(addressExternalId, addressDto);
	}
	
	@Operation(summary = "Delete a saved address", description = "Deletes the address identified by its external ID.")
	@DeleteMapping("/user/address/{addressExternalId}")
	public ResponseEntity<?> deleteUserAddress(@PathVariable String addressExternalId) {
		return service.deleteUserAddress(addressExternalId);
	}

}
