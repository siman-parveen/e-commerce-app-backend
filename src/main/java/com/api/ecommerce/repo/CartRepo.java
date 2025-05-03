package com.api.ecommerce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.ecommerce.entity.Cart;
import com.api.ecommerce.entity.User;

public interface CartRepo extends JpaRepository<Cart, Long> {
	
	public Optional<Cart> findByUser(User user);

}
