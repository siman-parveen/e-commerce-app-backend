package com.api.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.ecommerce.entity.Cart;

public interface CartRepo extends JpaRepository<Cart, Long> {

}
