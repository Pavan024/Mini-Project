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

import com.ecommerce.admin.entity.Product;
import com.ecommerce.admin.header.HeaderGenerator;
import com.ecommerce.admin.service.ProductService;

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
	public void testAddProduct() {

		when(productService.addProduct(product)).thenReturn(product);

		ResponseEntity<Product> response = productController.addProduct(product, null);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(product, response.getBody());

		ResponseEntity<Product> res = productController.addProduct(null, null);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals(null, res.getBody());
	}

	@Test
	public void testUpdateProduct() {

		when(productService.updateProduct(product)).thenReturn(product);

		ResponseEntity<Product> response = productController.updateProduct(product, null);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(product, response.getBody());

		ResponseEntity<Product> res = productController.updateProduct(null, null);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals(null, res.getBody());
	}

	@Test
	public void testDeleteProduct() {

		when(productService.deleteProduct(product.getId())).thenReturn("Deleted");

		ResponseEntity<String> response = productController.deleteProduct(product.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Deleted", response.getBody());
	}

	@Test
	public void testGetAllProducts() {
		products.add(product);

		when(productService.getAllProduct()).thenReturn(products);

		ResponseEntity<List<Product>> response = productController.getAllProducts();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(products, response.getBody());
	}
}