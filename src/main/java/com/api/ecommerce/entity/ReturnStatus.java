package com.api.ecommerce.entity;

public enum ReturnStatus {
    NONE,            // No return requested (default state)
    REQUESTED,       // Customer requested a return
    APPROVED,        // Return has been approved
    REJECTED,        // Return request rejected
    IN_TRANSIT,      // Customer has shipped the return
    RECEIVED,        // Returned item received
    REFUNDED         // Refund issued
}
