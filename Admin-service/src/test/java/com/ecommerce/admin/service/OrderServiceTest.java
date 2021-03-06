package com.ecommerce.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecommerce.admin.entity.CartItems;
import com.ecommerce.admin.entity.Order;
import com.ecommerce.admin.entity.User;
import com.ecommerce.admin.repository.OrderRepository;

@SpringBootTest
public class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderService orderService;

	List<CartItems> items = new ArrayList<CartItems>();
	List<Order> orders = new ArrayList<Order>();
	User user = new User("", "Pavan123", "SaiPavan", "Kumar", "saipavan@gmail.com", "7996155024", "Hyderabad",
			"Pavan123", "user");
	Order order = new Order("", LocalDate.parse("2022-01-12"), 1500.0f, "COD", items, user);

	@Test
	public void testGetOrderById() {
		items.add(new CartItems("", 5, 1000.0f, ""));

		when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
		assertEquals(order, orderService.getOrderById(order.getId()));
	}

	@Test
	public void testGetAllOrdersById() {
		items.add(new CartItems("", 5, 1000.0f, ""));
		orders.add(order);

		when(orderRepository.findAllOrdersByUserId(user.getId())).thenReturn(orders);
		assertEquals(orders, orderService.getAllOrdersById(user.getId()));
	}

	@Test
	public void testGetAllOrders() {
		items.add(new CartItems("", 5, 1000.0f, ""));
		orders.add(order);

		when(orderRepository.findAll()).thenReturn(orders);
		assertEquals(orders, orderService.getAllOrders());
	}
}
