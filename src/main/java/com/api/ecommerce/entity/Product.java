package com.api.ecommerce.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long entityId;
	
	@Column(nullable = false, unique = true)
	private String externalId;
	
	@Column(nullable = false)
	private String productName;
	
	@Column(nullable = false)
	private String description;
	
	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal price;
	
	@PositiveOrZero
	@Column(nullable = false)
	private Integer stock;

	@Min(0)
	@Max(100)
	@Column(nullable = false)
	private int discountPercentage;

	@Column(nullable = false)
	private boolean active = true;

	@Version
	private Long version;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Review> reviews = new ArrayList<Review>();

}
