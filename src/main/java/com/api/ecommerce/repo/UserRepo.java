package com.api.ecommerce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.ecommerce.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

	public Optional<User> findByExternalId(String userExternalId);

	public Optional<User> findByEmailId(String emailId);

	public Optional<User> findByEmailIdOrPhoneNumber(String emailId, String phoneNumber);

}
