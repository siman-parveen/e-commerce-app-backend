package com.api.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.AddressRequestDTO;
import com.api.ecommerce.dto.response.AddressResponseDTO;
import com.api.ecommerce.entity.User;
import com.api.ecommerce.entity.UserAddress;
import com.api.ecommerce.repo.UserAddressRepo;
import com.api.ecommerce.response.Response;

@Service
public class AddressService {
	
	@Autowired
	private UserAddressRepo repo;
	
	@Autowired
	private CommonService service;

	public ResponseEntity<?> addUserAddress(AddressRequestDTO addressDto) {
		
		Response<AddressResponseDTO> response = new Response<AddressResponseDTO>();
		
		UserAddress address = service.convertAddressRequestDtoToAddress(addressDto);
		
		repo.save(address);
		
		response.setSuccess(true);
		response.setMessage("Address added successfully!!");
		response.setData(service.convertAddressToAddressResponseDTO(address));
		
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> updateUserAddress(String addressExternalId, AddressRequestDTO addressDto) {
		
		Response<AddressResponseDTO> response = new Response<AddressResponseDTO>();
		
		Optional<UserAddress> address =  repo.findByExternalId(addressExternalId);
		
		if(address.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No Addresses Found with given id!!");
			return ResponseEntity.ok().body(response);
		}

		UserAddress userAddress = address.get();
		
		if(service.isUpdated(userAddress.getAddressLine(), addressDto.getAddressLine())) {
			userAddress.setAddressLine(addressDto.getAddressLine());
		}
		
		if(service.isUpdated(userAddress.getAddressName(), addressDto.getAddressName())) {
			userAddress.setAddressName(addressDto.getAddressName());
		}
		
		if(service.isUpdated(userAddress.getCity(), addressDto.getCity())) {
			userAddress.setCity(addressDto.getCity());
		}
		
		if(service.isUpdated(userAddress.getCountry(), addressDto.getCountry())) {
			userAddress.setCountry(addressDto.getCountry());
		}
		
		if(service.isUpdated(userAddress.getPincode(), addressDto.getPincode())) {
			userAddress.setPincode(addressDto.getPincode());
		}
		
		if(service.isUpdated(userAddress.getState(), addressDto.getState())) {
			userAddress.setState(userAddress.getState());
		}
		
		repo.save(userAddress);
		
		response.setSuccess(true);
		response.setMessage("Address updated successfully!!");
		response.setData(service.convertAddressToAddressResponseDTO(address.get()));
		
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> deleteUserAddress(String addressExternalId) {
		Response<?> response = new Response<>();

		Optional<UserAddress> address = repo.findByExternalId(addressExternalId);
		
		if(address.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No Addresses Found with given id!!");
			return ResponseEntity.ok().body(response);
		}
		
		repo.deleteById(address.get().getEntityID());
		response.setMessage("Address Deleted Successfully!!");
		response.setSuccess(true);
			
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> retrieveAddressForUser(String userExternalId) {
		Response<List<AddressResponseDTO>> response = new Response<>();
		
		User user = service.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage("User not found!!");
			return ResponseEntity.ok().body(response);
		}
		
		List<UserAddress> addresses = user.getAddress();
		
		if(addresses == null || addresses.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No Addresses Found For User!!");
			return ResponseEntity.ok().body(response);
		}
		
		response.setSuccess(true);
		response.setMessage("User Addresses retrieved successfully!!");
		response.setData(addresses.stream().map(address -> service.convertAddressToAddressResponseDTO(address)).toList());
		
		return ResponseEntity.ok().body(response);
	}

}
