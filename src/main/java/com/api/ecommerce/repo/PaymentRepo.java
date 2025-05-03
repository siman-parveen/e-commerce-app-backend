package com.api.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.ecommerce.entity.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

}
