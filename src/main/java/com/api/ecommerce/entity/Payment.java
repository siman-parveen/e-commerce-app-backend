package com.api.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long entityId;

	@Column(nullable = false, unique = true)
	private String transactionId;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal totalAmount;

	@Column(nullable = false)
	private LocalDateTime paymentDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentMode paymentMode;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus paymentStatus;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id", nullable = false, unique = true)
	private Order order;

}
