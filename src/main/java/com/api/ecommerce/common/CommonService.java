package com.api.ecommerce.common;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.ecommerce.dto.request.OrderRequestDTO;
import com.api.ecommerce.dto.request.PaymentRequestDTO;
import com.api.ecommerce.dto.request.ProductQuantityRequestDTO;
import com.api.ecommerce.dto.request.ProductRequestDTO;
import com.api.ecommerce.dto.request.UserRequestDTO;
import com.api.ecommerce.dto.response.CartItemResponseDTO;
import com.api.ecommerce.dto.response.CartResponseDTO;
import com.api.ecommerce.dto.response.OrderItemResponseDTO;
import com.api.ecommerce.dto.response.OrderResponseDTO;
import com.api.ecommerce.dto.response.PaymentResponseDTO;
import com.api.ecommerce.dto.response.UserResponseDTO;
import com.api.ecommerce.entity.Cart;
import com.api.ecommerce.entity.CartItem;
import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.OrderItem;
import com.api.ecommerce.entity.Payment;
import com.api.ecommerce.entity.Product;
import com.api.ecommerce.entity.User;
import com.api.ecommerce.entity.UserAddress;
import com.api.ecommerce.repo.OrderRepo;
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
	private OrderRepo orderRepo;
	
	public boolean isUpdated(Object oldValue, Object newValue) {
		return (!oldValue.equals(newValue));
	}
	
	public Product findProductByExternalId(String productExternalId) {
		return productRepo.findByExternalId(productExternalId).orElseThrow(() -> new RuntimeException(CommonConstant.NO_PRODUCT_FOUND));
	}
	
	public User findUserByExternalId(String userExternalId) {
		return userRepo.findByExternalId(userExternalId).orElseThrow(() -> new RuntimeException(CommonConstant.NO_USER_FOUND));
	}
	
	public UserAddress findUserAddressByExternalId(String userAddressExternalId) {
		return addressRepo.findByExternalId(userAddressExternalId).orElseThrow(() -> new RuntimeException("User Address not found!!"));
	}
	
	public Order findOrderByExternalId(String orderExternalId) {
		return orderRepo.findByExternalId(orderExternalId).orElseThrow(() -> new RuntimeException("No order (with given id) found!!"));
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
		user.setPassword(generateExternalIdForEntities(""));
		return user;
	}
	
	public Order convertOrderRequestDtoToOrder(OrderRequestDTO orderRqst) {
		Order order = new Order();
		
		User user = findUserByExternalId(orderRqst.getUserExternalId());
		order.setUser(user);

		UserAddress deliveryAddress = findUserAddressByExternalId(orderRqst.getDeliveryAddressExternalId());
		order.setDeliveryAddress(deliveryAddress);

		order.setExternalId(generateExternalIdForEntities("ORD"));
		order.setOrderDate(LocalDateTime.now());
		order.setDeductions(orderRqst.getDeductions());
		order.setPhoneNumber(orderRqst.getPhoneNumber());
		order.setTotalAmount(orderRqst.getTotalAmount());
		
		return order;
	}

	public OrderItem convertProductQuantityRequestDtoToOrderItem(ProductQuantityRequestDTO item, Order order) {
		OrderItem orderItem = new OrderItem();
		orderItem.setIsDelivered(false);
		
		Product product = findProductByExternalId(item.getProductExternalId());
		
		orderItem.setProduct(product);
		orderItem.setQuantity(item.getQuantity());
		orderItem.setOrder(order);
		return orderItem;
		
	}
	
	public String generateExternalIdForEntities(String prefix) {
		return prefix + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
	}

	public UserResponseDTO convertUsersToUserResponseDTO(User user) {
		UserResponseDTO dto = new UserResponseDTO();
		dto.setUserExternalId(user.getExternalId());
		dto.setUserName(user.getUserName() == null ? user.getUserName() : "");
		dto.setEmailId(user.getEmailId());
		dto.setPhoneNumber(user.getPhoneNumber());
		
		dto.setAddressCount(user.getAddress().size());
		dto.setCartItemsCount(user.getCart().getCartItem().size());
		dto.setOrdersCount(user.getOrders().size());
		dto.setRatingsCount(user.getRatings().size());

		return dto;
	}


	public OrderResponseDTO convertOrderToOrderResponseDto(Order order) {
		OrderResponseDTO dto = new OrderResponseDTO();
		return dto;
	}

	public Payment createNewPaymentForOrder(Order order, PaymentRequestDTO paymentDTO) {
		Payment payment = new Payment();
		payment.setTotalAmount(paymentDTO.getTotalAmount());
		payment.setOrder(order);
		payment.setPaymentDate(LocalDateTime.now());
		payment.setPaymentMode(paymentDTO.getPaymentMode());
		payment.setPaymentStatus(paymentDTO.getPaymentStatus());
		payment.setTransactionId(paymentDTO.getTransactionId());
		return payment;
	}

	public PaymentResponseDTO convertPaymentToPaymentResponseDTO(Payment payment) {
		PaymentResponseDTO dto = new PaymentResponseDTO();
		return dto;
	}
	
	public CartResponseDTO convertCartToCartResponseDTO(Cart cart) {
		CartResponseDTO dto = new CartResponseDTO();
		dto.setCartExternalId(null);
		dto.setCartItemCount(0);
		dto.setCartItemResponseDto(null);
		dto.setTotalAmount(0);
		dto.setUserExternalId(null);
		return dto;
	}
	
	public CartItemResponseDTO convertCartItemToCartItemResponseDTO(CartItem cartItem) {
		CartItemResponseDTO dto = new CartItemResponseDTO();
		dto.setCartItemExternalId(null);
		dto.setProductExternalId(null);
		dto.setProductName(null);
		dto.setQuantity(0);
		dto.setTotalPrice(0);
		return dto;
	}
	
	
	public OrderResponseDTO convertOrderToOrderResponseDTO(Order order) {
		OrderResponseDTO dto = new OrderResponseDTO();
		return dto;
	}
	
	public OrderItemResponseDTO convertOrderItemToOrderItemResponseDTO(OrderItem orderItem) {
		OrderItemResponseDTO dto = new OrderItemResponseDTO();
		return dto;
	}
	
}
