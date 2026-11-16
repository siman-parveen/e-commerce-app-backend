package com.api.ecommerce.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long entityId;
	
	@Column(nullable = false, unique = true)
	private String externalId;
	
	@Column(nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String emailId;
	
	@Column(nullable = false)
	@JsonSerialize(using = PasswordMaskSerializer.class)
	private String password;
	
	@Column(nullable = false)
	private String phoneNumber;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserAddress> address = new ArrayList<>();
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Cart cart = new Cart();

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Wallet wallet = new Wallet();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Review> reviews = new ArrayList<>();

	static class PasswordMaskSerializer extends JsonSerializer<String> {
		@Override
		public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) {
			try {
				gen.writeString("******");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
