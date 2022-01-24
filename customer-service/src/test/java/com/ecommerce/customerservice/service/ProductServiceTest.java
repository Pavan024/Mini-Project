package com.ecommerce.customerservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecommerce.customerservice.entity.Product;
import com.ecommerce.customerservice.repository.ProductRepository;

@SpringBootTest
public class ProductServiceTest {
	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductService productService;
	
	List<Product> products = new ArrayList<Product>();
	Product product = new Product("", "Electronics", "Laptop", "i5 11th gen", 30000.0f, 50, "");

	
	@Test
	public void testGetAllProducts() {
		products.add(product);
		
		when(productRepository.findAll()).thenReturn(products);
		assertEquals(products, productService.getAllProducts());
	}
	
	@Test
	public void testGetAllProductsByName() {
		products.add(product);
		
		when(productRepository.findByName(product.getName())).thenReturn(products);
		assertEquals(products, productService.getAllProductsByName(product.getName()));
	}
	
	@Test
	public void testGetAllProductByCategory() {
		products.add(product);
		
		when(productRepository.findAllByCategoryName(product.getCategoryName() )).thenReturn(products);
		assertEquals(products, productService.getAllProductByCategory(product.getCategoryName()));
	}
	
	@Test
	public void testGetProductById() {
		
		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		assertEquals(product, productService.getProductById(product.getId()));
	}
	
	@Test
	public void testUpdateStock() {
		
		when(productRepository.save(product)).thenReturn(product);
		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		
		assertEquals(product, productService.updateStock(product.getId(), 5));
	}
}
