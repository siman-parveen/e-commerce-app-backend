package com.api.ecommerce.common;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.api.ecommerce.dto.request.ProductRequestDTO;
import com.api.ecommerce.dto.request.UserRequestDTO;
import com.api.ecommerce.entity.Product;
import com.api.ecommerce.entity.User;

@Service
public class CommonService {
	
	public boolean isUpdated(Object oldValue, Object newValue) {
		return (!oldValue.equals(newValue));
	}

	public Product convertProductRequestDTOToProduct(ProductRequestDTO productDto) {
		Product product = new Product();
		product.setProductName(productDto.getProductName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setStock(productDto.getStock());
		return product;
	}

	public User convertUserRequestDTOToUser(UserRequestDTO userDto) {
		User user = new User();
		user.setUserName(userDto.getUserName());
		//user.setAddress(userDto.getAddress());
		return user;
	}
	
	public String generateExternalIdForEntities(String prefix) {
		return prefix + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
	}
	


}
