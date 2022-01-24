package com.ecommerce.customerservice.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity class for cart Items
 * @author saipavan
 */
@Document
public class CartItems {

	@Id
	private String id;

	@NotNull
	private String productId;

	@NotNull
	private int quantity;

	private float subTotal;

	@NotNull
	private String userId;

	private List<Order> orders;

	public CartItems() {
		super();
	}

	public CartItems(String productId, int quantity, float subTotal, String userId) {
		super();
		this.productId = productId;
		this.quantity = quantity;
		this.subTotal = subTotal;
		this.userId = userId;
	}

	public CartItems(String id, String productId, int quantity, float subTotal, String userId, List<Order> orders) {
		super();
		this.id = id;
		this.productId = productId;
		this.quantity = quantity;
		this.subTotal = subTotal;
		this.userId = userId;
		this.orders = orders;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public float getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(float totalAmount) {
		this.subTotal = totalAmount;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}