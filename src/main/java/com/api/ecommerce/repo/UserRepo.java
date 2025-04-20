package com.api.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.ecommerce.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

}
