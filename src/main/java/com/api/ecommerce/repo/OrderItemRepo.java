package com.api.ecommerce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.OrderItem;


@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

	public Optional<OrderItem> findByOrder(Order order);

}
