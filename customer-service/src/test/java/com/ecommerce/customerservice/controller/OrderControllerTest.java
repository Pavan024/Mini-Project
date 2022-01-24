package com.ecommerce.customerservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.ecommerce.customerservice.entity.CartItems;
import com.ecommerce.customerservice.entity.Order;
import com.ecommerce.customerservice.entity.Product;
import com.ecommerce.customerservice.entity.User;
import com.ecommerce.customerservice.header.HeaderGenerator;
import com.ecommerce.customerservice.service.CartService;
import com.ecommerce.customerservice.service.OrderService;

@SpringBootTest
public class OrderControllerTest {

	@Mock
	private OrderService orderService;

	@Mock
	private CartService cartService;
	
	@Mock
	private HeaderGenerator headerGenerator;

	@InjectMocks
	private OrderController orderController;

	List<CartItems> items = new ArrayList<CartItems>();
	List<Product> products = new ArrayList<Product>();
	User user = new User("", "Pavan123", "SaiPavan", "Kumar", "saipavan@gmail.com", "7996155024", "Hyderabad",
			"Pavan123", "user");
	Order order = new Order("", LocalDate.now(), 1500.0f, "COD", items, products, user);

	@Test
	public void testConfirmOrder() {
		items.add(new CartItems("", 5, 1000.0f, ""));
		products.add(new Product("", "Electronics", "Laptop", "i5 11th gen", 30000.0f, 50, ""));

		when(orderService.saveOrder(order)).thenReturn(order);
		when(orderService.placeOrder(user.getId())).thenReturn(order);

		orderService.updateStock(user.getId());
		verify(orderService, times(1)).updateStock(user.getId());
		cartService.deleteCart(order.getId(), user.getId());
		verify(cartService, times(1)).deleteCart(order.getId(), user.getId());

		ResponseEntity<Order> response = orderController.confirmOrder(user.getId(), null);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(order, response.getBody());
		
		ResponseEntity<Order> res = orderController.confirmOrder(null, null);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals(null, res.getBody());
	}

	@Test
	public void testGetOneOrderById() {

		when(orderService.getOrderById(order.getId())).thenReturn(order);

		ResponseEntity<Order> response = orderController.getOneOrderById(order.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(order, response.getBody());
	}

	@Test
	public void testGetAllOrdersByUserId() {
		List<Order> orders = new ArrayList<Order>();
		orders.add(order);

		when(orderService.getAllOrdersById(user.getId())).thenReturn(orders);

		ResponseEntity<List<Order>> response = orderController.getAllOrdersByUserId(user.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(orders, response.getBody());
	}

}