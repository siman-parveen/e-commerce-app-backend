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

import com.api.ecommerce.dto.request.AddressRequestDTO;
import com.api.ecommerce.dto.response.AddressResponseDTO;
import com.api.ecommerce.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AddressRestController {
	
	@Autowired
	private AddressService service;
	
	@Operation(summary = "Add User Address", description = "API returns all the registered users")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Users retrieved successfully!!", content = @Content(mediaType = "/application/json", array = @ArraySchema(schema = @Schema(implementation = AddressResponseDTO.class)))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@GetMapping("/user/{userExternalId}/address")
	public ResponseEntity<?> retrieveAddressForUser(@PathVariable String userExternalId) {
		return service.retrieveAddressForUser(userExternalId);
	}
	
	@Operation(summary = "Add User Address", description = "API returns all the registered users")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Users retrieved successfully!!", content = @Content(mediaType = "/application/json", array = @ArraySchema(schema = @Schema(implementation = AddressResponseDTO.class)))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@PostMapping("/user/address")
	public ResponseEntity<?> addUserAddress(@Valid @RequestBody AddressRequestDTO addressDto) {
		return service.addUserAddress(addressDto);
	}
	
	@Operation(summary = "Update User Address", description = "API returns all the registered users")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Users retrieved successfully!!", content = @Content(mediaType = "/application/json", array = @ArraySchema(schema = @Schema(implementation = AddressResponseDTO.class)))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@PatchMapping("/user/address/{addressExternalId}")
	public ResponseEntity<?> updateUserAddress(@PathVariable String addressExternalId, @Valid @RequestBody AddressRequestDTO addressDto) {
		return service.updateUserAddress(addressExternalId, addressDto);
	}
	
	@Operation(summary = "Delete User Address", description = "API returns all the registered users")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Users retrieved successfully!!", content = @Content(mediaType = "/application/json")),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@DeleteMapping("/user/address/{addressExternalId}")
	public ResponseEntity<?> deleteUserAddress(@PathVariable String addressExternalId) {
		return service.deleteUserAddress(addressExternalId);
	}

}
