package com.api.ecommerce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.ecommerce.entity.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

	List<Review> findByUser_EntityId(Long entityId);

	List<Review> findByProduct_EntityId(Long entityId);

	Optional<Review> findByExternalId(String reviewExternalId);

}
