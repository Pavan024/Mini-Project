package com.ecommerce.admin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ecommerce.admin.entity.Order;
import com.ecommerce.admin.entity.User;
import com.ecommerce.admin.header.HeaderGenerator;
import com.ecommerce.admin.service.OrderService;

@SpringBootTest
public class OrderControlllerTest {
	
	@Mock
	private OrderService orderService;

	@Mock
	private HeaderGenerator headerGenerator;
	
	@InjectMocks
	private OrderController orderController;
	
	List<Order> orders = new ArrayList<Order>();
	User user = new User("", "Pavan123", "SaiPavan", "Kumar", "saipavan@gmail.com", "7996155024", "Hyderabad",
			"Pavan123", "user");
	Order order =new Order("", LocalDate.parse("2022-01-12"), 2500.0f, "COD", null, user);
	
	@Test
	public void testGetReceiptById() {
		orders.add(order);
		
		when(orderService.getOrderById(order.getId())).thenReturn(order);
		
		ResponseEntity<Order> response = orderController.getReceiptById(order.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(order, response.getBody());
	}
	
	@Test
	public void testGetAllOrdersByUserId() {
		orders.add(order);
		
		when(orderService.getAllOrdersById(order.getUser().getId())).thenReturn(orders);
		
		ResponseEntity<List<Order>> response = orderController.getAllOrdersByUserId(order.getUser().getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(orders, response.getBody());
	}
	
	@Test
	public void testGetAllOrders() {
		orders.add(order);
		
		when(orderService.getAllOrders()).thenReturn(orders);
		
		ResponseEntity<List<Order>> response = orderController.getAllOrders();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(orders, response.getBody());
	}
}
