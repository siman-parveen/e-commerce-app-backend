package com.api.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.ecommerce.entity.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
