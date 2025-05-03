package com.api.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.ecommerce.entity.OrderItem;


@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

}
