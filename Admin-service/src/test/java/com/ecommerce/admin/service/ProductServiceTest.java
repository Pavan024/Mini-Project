package com.ecommerce.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecommerce.admin.entity.Product;
import com.ecommerce.admin.repository.ProductRepository;

@SpringBootTest
public class ProductServiceTest {

	@Mock
	private ProductRepository repository;

	@InjectMocks
	private ProductService productService;
	
	List<Product> products = new ArrayList<Product>();
	Product product = new Product("", "Electronics", "Laptop", "i5 11th gen", 30000.0f, 50, "");

	@Test
	public void testGetAllProduct() {
		products.add(product);

		when(repository.findAll()).thenReturn(products);// Mocking
		assertEquals(products.size(), productService.getAllProduct().size());
	}

	@Test
	public void testAddProduct() {

		when(repository.save(product)).thenReturn(product);// Mocking
		assertEquals(product, productService.addProduct(product));
	}

	@Test
	public void testUpdateProduct() {
		products.add(product);

		when(repository.findAll()).thenReturn(products);// Mocking
		when(repository.save(product)).thenReturn(product);// Mocking
		assertEquals(product, productService.updateProduct(product));
	}

	@Test
	public void testDeleteProduct() {
		
		assertEquals("Product Deleted", productService.deleteProduct(product.getId()));
		verify(repository, times(1)).deleteById(product.getId());
	}
}
