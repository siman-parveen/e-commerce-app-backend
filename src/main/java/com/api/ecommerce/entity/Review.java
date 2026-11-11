package com.api.ecommerce.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long entityID;
	
	 @Column(nullable = false, unique = true)
	private String externalId;
	 
	@Column(nullable = false)
	private Integer rating;
	
	@Column(nullable = false)
	private String comment;
	
	@ManyToOne
	@JoinColumn(name = "products_id", nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "users_id", nullable = false)
	private User user;
	
	private LocalDateTime reviewedAt;

}
