package com.ecommerce.admin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ecommerce.admin.entity.User;
import com.ecommerce.admin.header.HeaderGenerator;
import com.ecommerce.admin.service.AdminService;

@SpringBootTest
public class AdminControllerTest {

	@Mock
	private AdminService adminService;

	@Mock
	private HeaderGenerator headerGenerator;

	@InjectMocks
	private AdminController adminController;

	User user = new User("", "Pavan123", "SaiPavan", "Kumar", "saipavan@gmail.com", "7996155024", "Hyderabad",
			"Pavan123", "admin");

	@Test
	public void testLogin() {

		when(adminService.login(user.getMobileNumber(), user.getPassword())).thenReturn("Login");
		when(adminService.login(user.getUserName(), user.getPassword())).thenReturn("Login");

		ResponseEntity<String> response = adminController.login(user.getMobileNumber(), user.getPassword());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Login", response.getBody());

		ResponseEntity<String> res = adminController.login(user.getUserName(), user.getPassword());
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("Login", res.getBody());
	}

	@Test
	public void testRestPassword() {

		when(adminService.resetPassword(user.getMobileNumber(), user.getPassword(), user.getPassword()))
				.thenReturn("Reset");
		when(adminService.resetPassword(user.getUserName(), user.getPassword(), user.getPassword()))
				.thenReturn("Reset");

		ResponseEntity<String> response = adminController.resetPassword(user.getMobileNumber(), user.getPassword(),
				user.getPassword());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Reset", response.getBody());

		ResponseEntity<String> res = adminController.resetPassword(user.getUserName(), user.getPassword(),
				user.getPassword());
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("Reset", res.getBody());
	}
	
	@Test
	public void testUpdate() {
		
		when(adminService.update(user, user.getUserName())).thenReturn(user);
		
		ResponseEntity<User> response = adminController.update(user, null);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(user, response.getBody());

		ResponseEntity<User> res = adminController.update(null, null);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals(null, res.getBody());
	}
}
