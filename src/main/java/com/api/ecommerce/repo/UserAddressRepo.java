package com.api.ecommerce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.ecommerce.entity.UserAddress;

public interface UserAddressRepo extends JpaRepository<UserAddress, Long> {

	public Optional<UserAddress> findByExternalId(String userExternalId);

}
