package com.ecommerce.admin.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity class for seller
 * 
 * @author saipavan
 */
@Document
public class Seller {
	@Id
	private String id;

	@Size(min = 3, max = 15)
	@NotEmpty(message = "*Please provide your Seller name")
	private String name;

	@Size(min = 3, max = 30)
	@NotEmpty(message = "*Please provide your seller address")
	private String address;

	public Seller() {
		super();
	}

	public Seller(String id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Seller [id=" + id + ", name=" + name + ", address=" + address + "]";
	}

}
