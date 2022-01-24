package com.ecommerce.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecommerce.admin.entity.User;
import com.ecommerce.admin.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {
	
	@Mock
	private UserRepository repository;
	
	@InjectMocks
	private UserService userService;
	
	List<User> users = new ArrayList<User>();
	User user = new User("", "Pavan123", "SaiPavan", "Kumar", "saipavan@gmail.com", "7996155024", "Hyderabad",
			"Pavan123", "user");
	
	@Test
	public void testGetUser() {
		
		when(repository.findByUserName(user.getUserName())).thenReturn(user);
		assertEquals(user,userService.getUser(user.getUserName()));
	}
	
	@Test
	public void testGetAllUser() {
		users.add(user);
		
		when(repository.findAll()).thenReturn(users);
		assertEquals(users,userService.getAllUser());		
	}
}
