package com.api.ecommerce.security;

import com.api.ecommerce.repo.CartItemRepo;
import com.api.ecommerce.repo.OrderRepo;
import com.api.ecommerce.repo.PaymentRepo;
import com.api.ecommerce.repo.ReviewRepo;
import com.api.ecommerce.repo.UserAddressRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userAuthorization")
public class UserAuthorization {

    private final UserAddressRepo addressRepo;
    private final CartItemRepo cartItemRepo;
    private final OrderRepo orderRepo;
    private final PaymentRepo paymentRepo;
    private final ReviewRepo reviewRepo;

    public UserAuthorization(
            UserAddressRepo addressRepo,
            CartItemRepo cartItemRepo,
            OrderRepo orderRepo,
            PaymentRepo paymentRepo,
            ReviewRepo reviewRepo) {
        this.addressRepo = addressRepo;
        this.cartItemRepo = cartItemRepo;
        this.orderRepo = orderRepo;
        this.paymentRepo = paymentRepo;
        this.reviewRepo = reviewRepo;
    }

    public boolean isCurrentUser(String userExternalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userExternalId != null
                && authentication != null
                && authentication.isAuthenticated()
                && authentication.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"))
                && userExternalId.equals(authentication.getName());
    }

    public boolean ownsAddress(String addressExternalId) {
        return addressExternalId != null
                && addressRepo.findByExternalId(addressExternalId)
                        .map(address -> address.getUser() != null
                                && isCurrentUser(address.getUser().getExternalId()))
                        .orElse(false);
    }

    public boolean ownsCartItem(String cartItemExternalId) {
        return cartItemExternalId != null
                && cartItemRepo.findByExternalId(cartItemExternalId)
                        .map(item -> item.getCart() != null
                                && item.getCart().getUser() != null
                                && isCurrentUser(item.getCart().getUser().getExternalId()))
                        .orElse(false);
    }

    public boolean ownsOrder(String orderExternalId) {
        return orderExternalId != null
                && orderRepo.findByExternalId(orderExternalId)
                        .map(order -> order.getUser() != null
                                && isCurrentUser(order.getUser().getExternalId()))
                        .orElse(false);
    }

    public boolean ownsPaymentTransaction(String transactionId) {
        return transactionId != null
                && paymentRepo.findByTransactionId(transactionId)
                        .map(payment -> payment.getOrder() != null
                                && payment.getOrder().getUser() != null
                                && isCurrentUser(payment.getOrder().getUser().getExternalId()))
                        .orElse(false);
    }

    public boolean ownsReview(String reviewExternalId) {
        return reviewExternalId != null
                && reviewRepo.findByExternalId(reviewExternalId)
                        .map(review -> review.getUser() != null
                                && isCurrentUser(review.getUser().getExternalId()))
                        .orElse(false);
    }
}
