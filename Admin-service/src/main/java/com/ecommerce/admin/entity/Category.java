package com.ecommerce.admin.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity Class for Category
 * @author saipavan
 */
@Document
public class Category {

	@Id
	private String id;

	@Size(min = 3, max = 15)
	@NotEmpty(message = "*Please provide your category name")
	private String name;

	@Size(min = 3, max = 30)
	@NotEmpty(message = "*Please provide your category description")
	private String description;

	public Category() {

	}

	public Category(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}