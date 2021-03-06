package com.ecommerce.customerservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.customerservice.entity.Product;
import com.ecommerce.customerservice.repository.ProductRepository;

/** 
 * Product Service Class
 * @author saipavan
 */
@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository repository;

	/** 
	 * This method will fetch all product instances form DB and returns
	 * @return List
	 */
	public List<Product> getAllProducts() {
		return repository.findAll();
	}

	/** 
	 * This is used to search products based on product name
	 * @param name
	 * @return List
	 */
	public List<Product> getAllProductsByName(String name) {
		return repository.findByName(name);
	}

	/** 
	 * This is used to search products based on product category
	 * @param categoryName
	 * @return List
	 */
	public List<Product> getAllProductByCategory(String categoryName) {
		return repository.findAllByCategoryName(categoryName);
	}

	/** 
	 * This is used to search products based on product id
	 * @param id
	 * @return product instance
	 */
	public Product getProductById(String id) {
		return repository.findById(id).orElse(null);
	}

	/** 
	 * This is used to update the product stock
	 * @param id
	 * @param quantity
	 * @return product instance
	 */
	public Product updateStock(String id, int quantity) {
		Product product = repository.findById(id).orElse(null);
		int stock = product.getStock();
		product.setStock(stock - quantity);
		repository.save(product);
		return product;
	}

}
