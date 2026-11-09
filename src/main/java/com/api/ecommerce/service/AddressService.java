package com.api.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.api.ecommerce.dto.request.AddressRequestDTO;

public interface AddressService {
	public ResponseEntity<?> addUserAddress(AddressRequestDTO addressDto);
	public ResponseEntity<?> updateUserAddress(String addressExternalId, AddressRequestDTO addressDto);
	public ResponseEntity<?> deleteUserAddress(String addressExternalId);
	public ResponseEntity<?> retrieveAddressForUser(String userExternalId);
}
