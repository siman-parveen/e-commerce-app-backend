package com.api.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItem {
	
	@Id
	private Long id;

	@ManyToOne
	private Product product;

	private int quantity;

	@ManyToOne
    @JoinColumn(name = "cart_id")
	private Cart cart;
	
	public CartItem() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@Override
	public String toString() {
		return "CartItem [id=" + id + ", product=" + product + ", quantity=" + quantity + ", cart=" + cart + "]";
	}
	
}
