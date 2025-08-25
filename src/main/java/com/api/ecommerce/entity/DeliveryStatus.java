package com.api.ecommerce.entity;

public enum DeliveryStatus {
    PENDING,         // Item not yet shipped
    PROCESSING,      // Preparing for shipment
    SHIPPED,         // In transit
    DELIVERED,       // Delivered to customer
    FAILED,          // Delivery failed
    CANCELLED        // Delivery canceled before shipping
}
