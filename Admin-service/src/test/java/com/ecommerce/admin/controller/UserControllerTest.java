package com.ecommerce.admin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ecommerce.admin.entity.User;
import com.ecommerce.admin.header.HeaderGenerator;
import com.ecommerce.admin.service.UserService;

@SpringBootTest
public class UserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private HeaderGenerator headerGenerator;

	@InjectMocks
	private UserController userController;

	List<User> users = new ArrayList<User>();
	User user = new User("", "Pavan123", "SaiPavan", "Kumar", "saipavan@gmail.com", "7996155024", "Hyderabad",
			"Pavan123", "user");

	@Test
	public void testViewAllUser() {
		users.add(user);
		
		when(userService.getAllUser()).thenReturn(users);
		
		ResponseEntity<List<User>> response = userController.viewAllUser();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(users, response.getBody());

	}

	@Test
	public void testViewUser() {
		
		when(userService.getUser(user.getUserName())).thenReturn(user);
		
		ResponseEntity<User> response = userController.viewUser(user.getUserName());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(user, response.getBody());
	}

}