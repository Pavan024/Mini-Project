package com.ecommerce.customerservice.controller;

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

import com.ecommerce.customerservice.entity.CartItems;
import com.ecommerce.customerservice.header.HeaderGenerator;
import com.ecommerce.customerservice.service.CartService;

@SpringBootTest
public class CartControllerTest {

	@Mock
	private CartService cartService;

	@Mock
	private HeaderGenerator headerGenerator;

	@InjectMocks
	private CartController cartController;

	CartItems item = new CartItems("", 5, 1000.0f, "");

	@Test
	public void testGetCart() {
		List<CartItems> items = new ArrayList<CartItems>();
		items.add(new CartItems("", 5, 1000.0f, ""));
		items.add(new CartItems("", 1, 1500.0f, ""));

		when(cartService.getCartByUserId(items.get(0).getUserId())).thenReturn(items);

		ResponseEntity<List<CartItems>> response = cartController.getCart(items.get(0).getUserId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(items, response.getBody());
	}

	@Test
	public void testAddItemToCart() {
		
		when(cartService.addItemToCart(item.getProductId(), item.getQuantity(), item.getUserId())).thenReturn(item);

		ResponseEntity<CartItems> response = cartController.addItemToCart(item.getProductId(), item.getQuantity(),
				item.getUserId(), null);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(item, response.getBody());

		ResponseEntity<CartItems> res = cartController.addItemToCart("", 0, "", null);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals(null, res.getBody());
	}
	
	@Test
	public void testUpdateQuantityInCart() {

		when(cartService.updateCart(item.getProductId(), item.getQuantity(), item.getUserId())).thenReturn(item);
		
		ResponseEntity<CartItems> response = cartController.updateQuantityInCart(item.getProductId(), item.getQuantity(),
				item.getUserId(), null);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(item, response.getBody());

		ResponseEntity<CartItems> res = cartController.updateQuantityInCart("", 0, "", null);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals(null, res.getBody());
	}
	
	@Test
	public void testRemoveItemFromCart() {
		
		when(cartService.deleteItemFromCart(item.getProductId(), item.getUserId())).thenReturn("Item Removed");

		ResponseEntity<String> response = cartController.removeItemFromCart(item.getProductId(), item.getUserId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Item Removed", response.getBody());
	}
}
