package com.api.ecommerce.repo;

import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.ecommerce.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

	public Optional<Product> findByExternalId(String productExternalId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from Product p where p.externalId = :productExternalId")
	Optional<Product> findByExternalIdForUpdate(@Param("productExternalId") String productExternalId);

}
