package com.api.ecommerce.controller;

import com.api.ecommerce.response.Response;

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
import org.springframework.security.access.prepost.PreAuthorize;

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
	@PreAuthorize("@userAuthorization.isCurrentUser(#p0)")
	public ResponseEntity<?> retrieveAddressForUser(@PathVariable String userExternalId) {
		Response<?> response = service.retrieveAddressForUser(userExternalId);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Retrieve addresses for a user", description = "Returns the saved addresses associated with the specified user.")
	@PostMapping("/user/address")
	@PreAuthorize("@userAuthorization.isCurrentUser(#p0.userExternalId)")
	public ResponseEntity<?> addUserAddress(@Valid @RequestBody AddressRequestDTO addressDto) {
		Response<?> response = service.addUserAddress(addressDto);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Update a saved address", description = "Updates the address identified by its external ID using the supplied details.")
	@PatchMapping("/user/address/{addressExternalId}")
	@PreAuthorize("@userAuthorization.ownsAddress(#p0)")
	public ResponseEntity<?> updateUserAddress(@PathVariable String addressExternalId, @Valid @RequestBody AddressRequestDTO addressDto) {
		Response<?> response = service.updateUserAddress(addressExternalId, addressDto);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Delete a saved address", description = "Deletes the address identified by its external ID.")
	@DeleteMapping("/user/address/{addressExternalId}")
	@PreAuthorize("@userAuthorization.ownsAddress(#p0)")
	public ResponseEntity<?> deleteUserAddress(@PathVariable String addressExternalId) {
		Response<?> response = service.deleteUserAddress(addressExternalId);
		return ResponseEntity.ok().body(response);
	}

}
