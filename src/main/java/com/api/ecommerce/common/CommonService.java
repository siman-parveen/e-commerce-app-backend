package com.api.ecommerce.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.api.ecommerce.dto.request.AddressRequestDTO;
import com.api.ecommerce.dto.request.OrderRequestDTO;
import com.api.ecommerce.dto.request.PaymentRequestDTO;
import com.api.ecommerce.dto.request.ProductQuantityRequestDTO;
import com.api.ecommerce.dto.request.ProductRequestDTO;
import com.api.ecommerce.dto.request.ReviewRequestDTO;
import com.api.ecommerce.dto.request.UserRequestDTO;
import com.api.ecommerce.dto.response.AddressResponseDTO;
import com.api.ecommerce.dto.response.CartItemResponseDTO;
import com.api.ecommerce.dto.response.CartResponseDTO;
import com.api.ecommerce.dto.response.OrderItemResponseDTO;
import com.api.ecommerce.dto.response.OrderResponseDTO;
import com.api.ecommerce.dto.response.PaymentResponseDTO;
import com.api.ecommerce.dto.response.ProductResponseDTO;
import com.api.ecommerce.dto.response.ProductReviewResponseDTO;
import com.api.ecommerce.dto.response.ProductUserBroughtResponseDTO;
import com.api.ecommerce.dto.response.ReviewDTO;
import com.api.ecommerce.dto.response.ReviewResponseDTO;
import com.api.ecommerce.dto.response.UserResponseDTO;
import com.api.ecommerce.entity.Cart;
import com.api.ecommerce.entity.CartItem;
import com.api.ecommerce.entity.DeliveryStatus;
import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.OrderItem;
import com.api.ecommerce.entity.OrderStatus;
import com.api.ecommerce.entity.Payment;
import com.api.ecommerce.entity.PaymentStatus;
import com.api.ecommerce.entity.Product;
import com.api.ecommerce.entity.ReturnStatus;
import com.api.ecommerce.entity.Review;
import com.api.ecommerce.entity.User;
import com.api.ecommerce.entity.UserAddress;
import com.api.ecommerce.exception.ApiException;
import com.api.ecommerce.repo.OrderRepo;
import com.api.ecommerce.repo.PaymentRepo;
import com.api.ecommerce.repo.ProductRepo;
import com.api.ecommerce.repo.UserAddressRepo;
import com.api.ecommerce.repo.UserRepo;

@Service
public class CommonService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private UserAddressRepo addressRepo;
	
	@Autowired
	private PaymentRepo paymentRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	public boolean isUpdated(Object oldValue, Object newValue) {
		return (!oldValue.equals(newValue));
	}
	
	public Product findProductByExternalId(String productExternalId) {
		Optional<Product> product = productRepo.findByExternalId(productExternalId);
        return product.orElse(null);
    }
	
	public User findUserByExternalId(String userExternalId) {
		Optional<User> user = userRepo.findByExternalId(userExternalId);
        return user.orElse(null);
    }
	
	public UserAddress findUserAddressByExternalId(String userAddressExternalId) {
		Optional<UserAddress> address = addressRepo.findByExternalId(userAddressExternalId);
        return address.orElse(null);
    }
	
	public Order findOrderByExternalId(String orderExternalId) {
		Optional<Order> order = orderRepo.findByExternalId(orderExternalId);
        return order.orElse(null);
    }
	
	public Payment findPaymentByOrderId(Long ordersId) {
		Optional<Payment> payment = paymentRepo.findByOrder_EntityId(ordersId);
        return payment.orElse(null);
    }

	public Product convertProductRequestDTOToProduct(ProductRequestDTO productDto) {
		Product product = new Product();
		product.setExternalId(generateExternalIdForEntities("PRD"));
		product.setProductName(productDto.getProductName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setStock(productDto.getStock());
		return product;
	}

	public User convertUserRequestDTOToUser(UserRequestDTO userDto) {
		User user = new User();
		user.setUserName(userDto.getUserName());
		user.setExternalId(generateExternalIdForEntities("USR"));
		user.setEmailId(userDto.getEmailId());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setPassword(userDto.getPassword());
		return user;
	}
	
	public Order convertOrderRequestDtoToOrder(OrderRequestDTO orderRequest) {
		Order order = new Order();
		
		User user = findUserByExternalId(orderRequest.getUserExternalId());
		order.setUser(user);

		UserAddress deliveryAddress = findUserAddressByExternalId(orderRequest.getDeliveryAddressExternalId());
		order.setDeliveryAddress(deliveryAddress);

		order.setExternalId(generateExternalIdForEntities("ORD"));
		order.setOrderDate(LocalDateTime.now());
		order.setDeductions(orderRequest.getDeductions());
		order.setPhoneNumber(orderRequest.getPhoneNumber());
		order.setTotalAmount(orderRequest.getTotalAmount());
		order.setOrderStatus(OrderStatus.NEW);

		order.setOrderItems(orderRequest.getOrderItems().stream().map(item -> convertProductQuantityRequestDtoToOrderItem(item, order)).toList());
		
		order.setOrderReceiverName(orderRequest.getOrderReceiverName());
		
		return order;
	}

	public OrderItem convertProductQuantityRequestDtoToOrderItem(ProductQuantityRequestDTO item, Order order) {
		OrderItem orderItem = new OrderItem();
		
		Product product = findProductByExternalId(item.getProductExternalId());
		
		if(product == null) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, CommonConstant.PRODUCT_CANNOT_BE_NULL);
		}
		
		if(product.getStock() <= 0) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Product not available at the moment");
		}
		
		orderItem.setExternalId(generateExternalIdForEntities("ORDITM"));
		orderItem.setProduct(product);
		orderItem.setQuantity(item.getQuantity());
		orderItem.setDeliveryDate(null);
		orderItem.setDeliveryStatus(DeliveryStatus.PROCESSING);
		orderItem.setDiscountAmount(null);
		orderItem.setTaxAmount(null);
		orderItem.setOrder(order);
		orderItem.setRefundedAmount(null);
		orderItem.setReturnDate(null);
		orderItem.setReturnRequested(false);
		orderItem.setReturnStatus(ReturnStatus.NONE);
		orderItem.setUnitPrice(product.getPrice());
		orderItem.setTotalPrice(BigDecimal.valueOf(product.getPrice().doubleValue()*item.getQuantity()));
		return orderItem;
		
	}
	
	public String generateExternalIdForEntities(String prefix) {
		return prefix.toLowerCase() + UUID.randomUUID().toString().replace("-", "").substring(0, 20).toUpperCase();
	}

	public UserResponseDTO convertUsersToUserResponseDTO(User user) {
		UserResponseDTO dto = new UserResponseDTO();
		dto.setUserExternalId(user.getExternalId());
		dto.setUserName(user.getUserName() != null ? user.getUserName() : "");
		dto.setEmailId(user.getEmailId());
		dto.setPhoneNumber(user.getPhoneNumber());
		
		List<AddressResponseDTO> addresses = user.getAddress().stream().map(this::convertAddressToAddressResponseDTO).toList();
		
		dto.setAddress(addresses);
		
		dto.setAddressCount(user.getAddress().size());
		dto.setCartItemsCount(user.getCart() != null ? user.getCart().getCartItem().size() : 0);
		dto.setOrdersCount(user.getOrders().size());
		dto.setReviewsCount(user.getReviews().size());

		return dto;
	}


	public AddressResponseDTO convertAddressToAddressResponseDTO(UserAddress address) {
		AddressResponseDTO dto = new AddressResponseDTO();
		dto.setAddressExternalId(address.getExternalId());
		dto.setAddressLine(address.getAddressLine());
		dto.setAddressName(address.getAddressName());
		dto.setCity(address.getCity());
		dto.setCountry(address.getCountry());
		dto.setPinCode(address.getPinCode());
		dto.setState(address.getState());
		return dto;
	}
	
	public OrderResponseDTO convertOrderToOrderResponseDto(Order order) {
		OrderResponseDTO dto = new OrderResponseDTO();
		dto.setOrderExternalId(order.getExternalId());
		dto.setOrderItemCount(order.getOrderItems().size());
		dto.setOrderDate(order.getOrderDate());
		dto.setAddress(convertAddressToAddressResponseDTO(order.getDeliveryAddress()));
		
		dto.setCustomerExternalId(order.getUser().getExternalId());
		dto.setCustomerName(order.getUser().getUserName());
		
		dto.setOrderItemCount(order.getOrderItems().size());
		
		List<OrderItemResponseDTO> orderItems = order.getOrderItems().stream().map(this::convertOrderItemToOrderItemResponseDTO).toList();
		dto.setOrderItems(orderItems);
		
		BigDecimal cost = orderItems.stream()
				.map(item -> BigDecimal.valueOf(item.getQuantity())
						.multiply(item.getProductPrice()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		dto.setTotalCost(cost);

		dto.setStatus(order.getOrderStatus().name());
		
		Payment payment = findPaymentByOrderId(order.getEntityId());
		
		if(payment != null) {
			dto.setPayment(convertPaymentToPaymentResponseDTO(payment));
		}
		
		return dto;
	}

	public Payment makePaymentForOrder(String orderExternalId, PaymentRequestDTO paymentDTO) {
		Payment payment = new Payment();

		try {
			payment.setTotalAmount(paymentDTO.getTotalAmount());
			
			Order order = findOrderByExternalId(orderExternalId);
			
			if(order == null) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, CommonConstant.ORDER_CANNOT_BE_NULL);
			}
			
			payment.setOrder(order);
			payment.setPaymentDate(LocalDateTime.now());
			payment.setPaymentMode(paymentDTO.getPaymentMode());
			payment.setPaymentStatus(PaymentStatus.START);
			payment.setTransactionId(paymentDTO.getTransactionId());
		}catch(Exception ex) {
			payment.setPaymentStatus(PaymentStatus.FAILED);
		}
		return payment;
	}

	public PaymentResponseDTO convertPaymentToPaymentResponseDTO(Payment payment) {
		PaymentResponseDTO dto = new PaymentResponseDTO();
		dto.setOrderExternalId(payment.getOrder().getExternalId());
		dto.setPaymentMethod(payment.getPaymentMode().name());
		dto.setPaymentStatus(payment.getPaymentStatus().name());
		dto.setTotalAmount(payment.getOrder().getTotalAmount());
		dto.setTransactionId(payment.getTransactionId());
		dto.setTransactionDate(payment.getPaymentDate());
		return dto;
	}
	
	public CartResponseDTO convertCartToCartResponseDTO(Cart cart) {
		CartResponseDTO dto = new CartResponseDTO();
		dto.setCartExternalId(cart.getExternalId());
		dto.setCartItemCount(cart.getCartItem().size());
		
		List<CartItemResponseDTO> cartItems = cart.getCartItem().stream().map(this::convertCartItemToCartItemResponseDTO).toList();
		
		dto.setCartItemResponseDto(cartItems);
		
		dto.setTotalAmount(cartItems.stream()
				.map(item -> BigDecimal.valueOf(item.getQuantity() * item.getPrice().doubleValue()))
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		
		dto.setUserExternalId(cart.getUser().getExternalId());
		
		return dto;
	}
	
	public CartItemResponseDTO convertCartItemToCartItemResponseDTO(CartItem cartItem) {
		CartItemResponseDTO dto = new CartItemResponseDTO();
		dto.setCartItemExternalId(cartItem.getExternalId());
		dto.setProductExternalId(cartItem.getProduct().getExternalId());
		dto.setProductName(cartItem.getProduct().getProductName());
		dto.setQuantity(cartItem.getQuantity());
		dto.setPrice(cartItem.getProduct().getPrice());
		dto.setProductStock(cartItem.getProduct().getStock());
		return dto;
	}
	
	public OrderItemResponseDTO convertOrderItemToOrderItemResponseDTO(OrderItem orderItem) {
		OrderItemResponseDTO dto = new OrderItemResponseDTO();
		dto.setQuantity(orderItem.getQuantity());
		if(orderItem.getDeliveryDate() != null) {
			dto.setDeliveredDate(orderItem.getDeliveryDate());
		}
		
		dto.setProductName(orderItem.getProduct().getProductName());
		dto.setProductPrice(orderItem.getProduct().getPrice());
		
		dto.setOrderItemExternalId(orderItem.getExternalId());
		
		return dto;
	}

	public ProductResponseDTO convertProductToProductResponseDTO(Product product) {
		ProductResponseDTO dto = new ProductResponseDTO();
		dto.setProductExternalId(product.getExternalId());
		dto.setProductName(product.getProductName());
		dto.setStock(product.getStock());
		dto.setRatingsCount(product.getReviews().size());
		dto.setOrderedCount(product.getOrderItems().size());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setTotalRating(calculateProductRating(product.getReviews()));
		dto.setDiscountPercentage(0);
		return dto;
		
	}

	private double calculateProductRating(List<Review> reviews) {
		if(reviews == null || reviews.isEmpty()) {
			return 0;
		}
		return (double) (reviews.stream()
                .mapToInt(Review::getRating)
                .sum()) /reviews.size();
	}

	public UserAddress convertAddressRequestDtoToAddress(AddressRequestDTO addressDto) {
		UserAddress address = new UserAddress();
		address.setExternalId(generateExternalIdForEntities("ADR"));
		address.setAddressLine(addressDto.getAddressLine());
		address.setAddressName(addressDto.getAddressName());
		address.setCity(addressDto.getCity());
		address.setCountry(addressDto.getCountry());
		address.setPinCode(addressDto.getPinCode());
		address.setState(addressDto.getState());
		address.setUser(findUserByExternalId(addressDto.getUserExternalId()));
		return address;
	}

	public ReviewResponseDTO convertReviewToReviewResponseDTO(Review review) {
		ReviewResponseDTO dto = new ReviewResponseDTO();
		dto.setReviewExternalId(review.getExternalId());
		dto.setComment(review.getComment());
		dto.setCustomerName(review.getUser().getUserName());
		dto.setProductName(review.getProduct().getProductName());
		dto.setRating(review.getRating());
		dto.setReviewedAt(review.getReviewedAt());
		dto.setProductExternalId(review.getProduct().getExternalId());
		dto.setUserExternalId(review.getUser().getExternalId());
		return dto;
	}

	public Review convertReviewRequestDtoToReview(ReviewRequestDTO reviewDto) {
		Review review = new Review();
		review.setExternalId(generateExternalIdForEntities("RVW"));
		review.setUser(findUserByExternalId(reviewDto.getUserExternalId()));
		
		Product product = findProductByExternalId(reviewDto.getProductExternalId());
		
		if(product == null) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, CommonConstant.PRODUCT_CANNOT_BE_NULL);
		}
		
		review.setProduct(product);
		review.setRating(reviewDto.getRating());
		review.setComment(reviewDto.getComment());
		review.setReviewedAt(LocalDateTime.now());
		return review;
	}

	public ProductReviewResponseDTO convertReviewToProductReviewResponseDTO(List<Review> productReviews, Product product) {
		ProductReviewResponseDTO dto = new ProductReviewResponseDTO();
		dto.setProductExternalId(product.getExternalId());
		dto.setProductName(product.getProductName());
		dto.setReviews(productReviews.stream().map(this::convertReviewToReviewDTO).toList());
		return dto;
	}

	private ReviewDTO convertReviewToReviewDTO(Review review) {
		ReviewDTO dto = new ReviewDTO();
		dto.setUserExternalId(review.getUser().getExternalId());
		dto.setCustomerName(review.getUser().getUserName());
		dto.setRating(review.getRating());
		dto.setComment(review.getComment());
		dto.setReviewedAt(review.getReviewedAt());
		return dto;
	}

	public void updatePaymentStatus(Payment payment, PaymentStatus status) {
		payment.setPaymentStatus(status);
	}

	public ProductUserBroughtResponseDTO createProductUserBroughtResponseDTO(Review review, User user, OrderItem orderItem) {
		ProductUserBroughtResponseDTO dto = new ProductUserBroughtResponseDTO();
		
		Product product = orderItem.getProduct();
		
		dto.setProductExternalId(product.getExternalId());
		dto.setProductName(product.getProductName());
		
		if(review != null) {
			dto.setRatingExternalId(review.getExternalId());
			dto.setRating(review.getRating());
			dto.setComment(review.getComment());
			dto.setReviewedAt(review.getReviewedAt());
		}
		
		dto.setOrderExternalId(orderItem.getOrder().getExternalId());
		dto.setOrderStatus(orderItem.getOrder().getOrderStatus().name());
		dto.setPrice(orderItem.getTotalPrice());
		dto.setQuantity(orderItem.getQuantity());
		
		dto.setProductDeliveryDate(orderItem.getDeliveryDate());
		dto.setUserName(user.getUserName());
		
		return dto;
	}
	
}
