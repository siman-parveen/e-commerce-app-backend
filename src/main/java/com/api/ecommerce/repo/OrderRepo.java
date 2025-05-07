package com.api.ecommerce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.User;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

	Optional<List<Order>> findByUser(User user);

	Optional<Order> findByEntityIdAndUser(Long orderId, User user);

	Optional<Order> findByExternalId(String orderExternalId);

}
