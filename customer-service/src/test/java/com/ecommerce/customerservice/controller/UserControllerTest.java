package com.ecommerce.customerservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ecommerce.customerservice.entity.User;
import com.ecommerce.customerservice.header.HeaderGenerator;
import com.ecommerce.customerservice.service.UserService;

@SpringBootTest
public class UserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private HeaderGenerator headerGenerator;

	@InjectMocks
	private UserController userController;

	User user = new User("", "Pavan123", "SaiPavan", "Kumar", "saipavan@gmail.com", "7996155024", "Hyderabad",
			"Pavan123", "user");

	@Test
	public void testResgister() {

		when(userService.saveUser(user)).thenReturn(user);

		ResponseEntity<User> response = userController.register(user, null);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(user, response.getBody());

		ResponseEntity<User> res = userController.register(null, null);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals(null, res.getBody());
	}

	@Test
	public void testLogin() {

		when(userService.login(user.getMobileNumber(), user.getPassword())).thenReturn("Login");
		when(userService.login(user.getUserName(), user.getPassword())).thenReturn("Login");

		ResponseEntity<String> response = userController.login(user.getMobileNumber(), user.getPassword());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Login", response.getBody());

		ResponseEntity<String> res = userController.login(user.getUserName(), user.getPassword());
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("Login", res.getBody());
	}

	@Test
	public void testRestPassword() {

		when(userService.resetPassword(user.getMobileNumber(), user.getPassword(), user.getPassword()))
				.thenReturn("Reset");
		when(userService.resetPassword(user.getUserName(), user.getPassword(), user.getPassword())).thenReturn("Reset");

		ResponseEntity<String> response = userController.resetPassword(user.getMobileNumber(), user.getPassword(),
				user.getPassword());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Reset", response.getBody());

		ResponseEntity<String> res = userController.resetPassword(user.getUserName(), user.getPassword(),
				user.getPassword());
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("Reset", res.getBody());
	}

	@Test
	public void testUpdate() {

		when(userService.update(user, user.getUserName())).thenReturn(user);

		ResponseEntity<User> response = userController.update(user, null);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(user, response.getBody());

		ResponseEntity<User> res = userController.update(null, null);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals(null, res.getBody());
	}
}
