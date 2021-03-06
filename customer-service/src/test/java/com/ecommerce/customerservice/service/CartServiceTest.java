package com.ecommerce.customerservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecommerce.customerservice.entity.CartItems;
import com.ecommerce.customerservice.entity.Product;
import com.ecommerce.customerservice.repository.CartRepository;
import com.ecommerce.customerservice.repository.ProductRepository;

@SpringBootTest
public class CartServiceTest {

	@Mock
	private CartRepository cartRepository;

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private CartService cartService;

	List<CartItems> items = new ArrayList<CartItems>();
	CartItems item = new CartItems("", 5, 1000.0f, "");
	Product product = new Product("", "Electronics", "Laptop", "i5 11th gen", 30000.0f, 50, "");

	@Test
	public void testGetAllItemsFromCart() {
		items.add(item);

		when(cartRepository.findAll()).thenReturn(items);
		assertEquals(items, cartService.getAllItemsFromCart());
	}

	@Test
	public void testAddItemToCart() {

		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		when(cartRepository.save(item)).thenReturn(item);

		assertEquals(150000.0f, cartService.getSubTotalForItem(product, 5));
		assertThat(cartService.addItemToCart(product.getId(), 5, "").equals(item));
	}

	@Test
	public void testCheckIfItemIsExist() {
		items.add(item);

		assertThat(cartService.getAllItemsFromCart().equals(items));
		assertFalse(cartService.checkIfItemIsExist(item.getProductId()));
	}

//	@Test
//	public void testChangeItemQuantity() {
//
//		when(cartRepository.findById(item.getId())).thenReturn(Optional.of(item));
//
//		cartService.changeItemQuantity("", 5, "");
//		verify(cartRepository, times(1)).findById("");
//	}

	@Test
	public void testDeleteItemFromCart() {
		items.add(item);

		when(cartRepository.findByUserId(item.getUserId())).thenReturn(items);
		assertEquals("Product Removed From Cart",
				cartService.deleteItemFromCart(item.getProductId(), item.getUserId()));
		verify(cartRepository, times(1)).deleteById(item.getId());
	}

	@Test
	public void testGetCartByUserId() {
		items.add(item);

		when(cartRepository.findByUserId("")).thenReturn(items);
		assertEquals(items, cartService.getCartByUserId(""));
	}

	@Test
	public void testDeleteCart() {

		when(cartRepository.deleteByUserId("")).thenReturn(1);
		
		cartService.deleteCart("", "");
		verify(cartRepository, times(1)).deleteByUserId("");
	}

//	@Test
//	public void testRemove() {
//
//		when(cartRepository.deleteByIdNative("")).thenReturn(1);
//
//		assertEquals(1, cartService.remove(""));
//	}

	@Test
	public void testUpdateCart() {
		items.add(item);

		when(cartRepository.findById(item.getId())).thenReturn(Optional.of(item));
		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		when(cartRepository.save(item)).thenReturn(item);

		assertThat(cartService.getAllItemsFromCart().equals(items));
		assertFalse(cartService.checkIfItemIsExist(item.getProductId()));
//		cartService.changeItemQuantity("", 5, "");
//		verify(cartRepository, times(1)).findById("");

		assertEquals(item, cartService.updateCart(product.getId(), 5, item.getId()));
	}

	@Test
	public void testGetSubTotalForItem() {

		assertEquals(30000.0f, cartService.getSubTotalForItem(product, 1));
	}
}
