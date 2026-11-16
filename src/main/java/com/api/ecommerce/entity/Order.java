package com.api.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity(name = "orders")
@Data
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long entityId;

	@Column(nullable = false, unique = true)
	private String externalId;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal totalAmount;

	@Column(precision = 10, scale = 2)
	private BigDecimal deductions;

	@Column(nullable = false, updatable = false)
	private LocalDateTime orderDate;

	@Column(nullable = false)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus orderStatus;
	
	@Column(nullable = false)
	private String orderReceiverName;

	@ManyToOne
	@JoinColumn(name = "address_id", nullable = false)
	private UserAddress deliveryAddress;

	@ManyToOne
	@JoinColumn(name = "users_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();
	
}