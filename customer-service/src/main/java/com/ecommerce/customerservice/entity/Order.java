package com.ecommerce.customerservice.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity class for order details
 * 
 * @author saipavan
 */
@Document
public class Order {

	@Id
	private String id;

	private LocalDate date;

	private float totalAmount;

	private String typeOfTransaction;

	private List<CartItems> items;

	private List<Product> product;

	private User user;

	public Order() {

	}

	public Order(String id, LocalDate date, float totalAmount, String typeOfTransaction, List<CartItems> items,
			List<Product> product, User user) {
		super();
		this.id = id;
		this.date = date;
		this.totalAmount = totalAmount;
		this.typeOfTransaction = typeOfTransaction;
		this.items = items;
		this.product = product;
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTypeOfTransaction() {
		return typeOfTransaction;
	}

	public void setTypeOfTransaction(String typeOfTransaction) {
		this.typeOfTransaction = typeOfTransaction;
	}

	public List<CartItems> getItems() {
		return items;
	}

	public void setItems(List<CartItems> items) {
		this.items = items;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", totalAmount=" + totalAmount + ", typeOfTransaction="
				+ typeOfTransaction + ", items=" + items + ", user=" + user + ", product=" + product + "]";
	}

}
