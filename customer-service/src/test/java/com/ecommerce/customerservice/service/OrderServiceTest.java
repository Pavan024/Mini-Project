package com.ecommerce.customerservice.service;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.ecommerce.customerservice.entity.CartItems;
import com.ecommerce.customerservice.entity.Order;
import com.ecommerce.customerservice.entity.Product;
import com.ecommerce.customerservice.entity.User;
import com.ecommerce.customerservice.repository.OrderRepository;
import com.ecommerce.customerservice.repository.UserRepository;

@SpringBootTest
public class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private CartService cartService;

	@Mock
	private ProductService productService;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private OrderService orderService;

	List<CartItems> items = new ArrayList<CartItems>();
	List<Product> products = new ArrayList<Product>();
	User user = new User("", "Pavan123", "SaiPavan", "Kumar", "saipavan@gmail.com", "7996155024", "Hyderabad",
			"Pavan123", "user");
	Product product = new Product("", "Electronics", "Laptop", "i5 11th gen", 30000.0f, 50, "");
	Order order = new Order("", LocalDate.now(), 1500.0f, "COD", items, products, user);

	@Test
	public void testSaveOrder() {
		items.add(new CartItems("", 5, 1000.0f, ""));
		products.add(product);
		Order order = new Order("", LocalDate.now(), 1500.0f, "COD", null, products, user);

		when(orderRepository.save(order)).thenReturn(order);

		assertEquals(order, orderService.saveOrder(order));
	}

	@Test
	public void testPlaceOrder() {
		items.add(new CartItems("", 5, 1000.0f, ""));
		products.add(product);
		Order order = new Order("", LocalDate.now(), 1500.0f, "COD", null, products, user);

		when(userRepository.getUserById(user.getId())).thenReturn(user);
		when(cartService.getCartByUserId(user.getId())).thenReturn(items);
		when(productService.getProductById(product.getId())).thenReturn(product);

		orderService.countTotalPrice(items);

		assertThat(orderService.placeOrder(user.getId()).equals(order));
	}

	@Test
	public void testGetOrderById() {

		when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
		assertEquals(order, orderService.getOrderById(order.getId()));
	}

	@Test
	public void testGetAllOrdersById() {
		List<Order> orders = new ArrayList<Order>();
		orders.add(order);

		when(orderRepository.findAllOrdersByUserId("")).thenReturn(orders);
		assertEquals(orders, orderService.getAllOrdersById(""));
	}

	@Test
	public void testCountTotalPrice() {
		items.add(new CartItems("", 5, 1000.0f, ""));
		items.add(new CartItems("", 1, 1500.0f, ""));

		assertEquals(2500.0f, orderService.countTotalPrice(items));
	}

	@Test
	public void testUpdateStock() {
		items.add(new CartItems("", 5, 1000.0f, ""));
		String userId = items.get(0).getUserId();

		when(productService.updateStock(userId, items.get(0).getQuantity())).thenReturn(product);
		when(cartService.getCartByUserId(userId)).thenReturn(items);
		orderService.updateStock(userId);
	}
}
