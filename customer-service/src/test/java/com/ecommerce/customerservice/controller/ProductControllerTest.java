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

import com.ecommerce.customerservice.entity.Product;
import com.ecommerce.customerservice.header.HeaderGenerator;
import com.ecommerce.customerservice.service.ProductService;

@SpringBootTest
public class ProductControllerTest {

	@Mock
	private ProductService productService;

	@Mock
	private HeaderGenerator headerGenerator;

	@InjectMocks
	private ProductController productController;

	List<Product> products = new ArrayList<Product>();
	Product product = new Product("", "Electronics", "Laptop", "i5 11th gen", 30000.0f, 50, "");

	@Test
	public void testGetAllProducts() {
		products.add(product);

		when(productService.getAllProducts()).thenReturn(products);

		ResponseEntity<List<Product>> response = productController.getAllProducts();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(products, response.getBody());
	}

	@Test
	public void testgetAllProductsByName() {
		products.add(product);

		when(productService.getAllProductsByName(product.getName())).thenReturn(products);

		ResponseEntity<List<Product>> response = productController.getAllProductsByName(product.getName());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(products, response.getBody());
	}

	@Test
	public void testgetAllProductsByCategory() {
		products.add(product);

		when(productService.getAllProductByCategory(product.getCategoryName())).thenReturn(products);

		ResponseEntity<List<Product>> response = productController.getAllProductByCategory(product.getCategoryName());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(products, response.getBody());
	}

	@Test
	public void testGetProductById() {

		when(productService.getProductById(product.getId())).thenReturn(product);

		ResponseEntity<Product> response = productController.getOneProductById(product.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(product, response.getBody());
	}
}
