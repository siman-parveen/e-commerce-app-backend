package com.api.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.ecommerce.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

}
