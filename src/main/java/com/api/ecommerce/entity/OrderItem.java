package com.api.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Data
public class OrderItem {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;

    @Column(nullable = false, unique = true)
    private String externalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Positive
    @Column(nullable = false)
    private Integer quantity;
    
    @Positive
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;
    
    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal discountAmount;
    
    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal taxAmount;
    
    @Positive
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    private LocalDateTime deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus deliveryStatus;

    private Boolean returnRequested;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReturnStatus returnStatus;

    private BigDecimal refundedAmount;
    private LocalDateTime returnDate;

}
