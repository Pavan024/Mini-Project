package com.ecommerce.customerservice.entity;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ecommerce.customerservice.validation.ValidEmail;
import com.ecommerce.customerservice.validation.ValidPassword;

/**
 * Entity class for user
 * @author saipavan
 */
@Document
public class User {
	
	@Id
	private String id;
	
	@Size(min = 3, max = 15)
	@NotEmpty(message = "*Please provide your name")
	private String userName;
	
	@Size(min = 3, max = 15)
	@NotEmpty(message = "*Please provide your first name")
	private String firstName;
	
	@Size(min = 3, max = 15)
	@NotEmpty(message = "*Please provide your last name")
	private String lastName;
	
	@ValidEmail(message = "*Please provide an email")
	@NotEmpty(message = "*Please provide an email")
	private String emailId;
	
	@Size(min = 10, max = 10)
	@NotEmpty(message = "*Please provide your mobile number")
	private String mobileNumber;
	
	@NotEmpty(message = "*Please provide your address O")
	private String address;
	
	@ValidPassword
	@Length(min = 8, message = "*Your password must have at least 8 characters")
	@NotEmpty(message = "*Please provide your password")
	private String password;
	
	private String role;
	
	private List<Order> orders;

	public User(String id, String username, String firstName, String lastName, String emailId, String mobileNumber,
			String address, String password, String role) {
		super();
		this.id = id;
		this.userName = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.password = password;
		this.role = role;
	}

	public User(@NotEmpty(message = "*Please provide your name") String userName,
			@Length(min = 5, message = "*Your password must have at least 5 characters") @NotEmpty(message = "*Please provide your password") String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public User() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", emailId=" + emailId + ", mobileNumber=" + mobileNumber + ", address=" + address + ", password="
				+ password + ", role=" + role + ", orders=" + orders + "]";
	}
	
}
