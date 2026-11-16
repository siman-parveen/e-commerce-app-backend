package com.api.ecommerce.service;

import com.api.ecommerce.response.Response;


import com.api.ecommerce.dto.request.AddressRequestDTO;

public interface AddressService {
	public Response<?> addUserAddress(AddressRequestDTO addressDto);
	public Response<?> updateUserAddress(String addressExternalId, AddressRequestDTO addressDto);
	public Response<?> deleteUserAddress(String addressExternalId);
	public Response<?> retrieveAddressForUser(String userExternalId);
}
