package com.ecommerce.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admin.entity.Product;
import com.ecommerce.admin.repository.ProductRepository;

/**
 * Product Service class
 * 
 * @author saipavan
 */
@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository repository;

	/**
	 * This method is to view All Products available in DB
	 * 
	 * @return List
	 */
	public List<Product> getAllProduct() {
		return repository.findAll();
	}

	/**
	 * This method save new Product to DB
	 * 
	 * @param product
	 * @return product instance
	 */
	public Product addProduct(Product product) {
		return repository.save(product);
	}

	/**
	 * This method is to update product details
	 * 
	 * @param product
	 * @return product instance
	 */
	public Product updateProduct(Product product) {
		List<Product> products = repository.findAll();
		Product newProduct = null;
		for (Product newPro : products)
			if (newPro.getId().equals(product.getId()))
				newProduct = newPro;
		newProduct.setName(product.getName());
		newProduct.setCategoryName(product.getCategoryName());
		newProduct.setDescription(product.getDescription());
		newProduct.setPrice(product.getPrice());
		newProduct.setSellerId(product.getSellerId());
		newProduct.setStock(product.getStock());
		repository.save(newProduct);
		return newProduct;
	}

	/**
	 * This method is to remove product
	 * 
	 * @param id
	 * @return
	 */
	public String deleteProduct(String id) {
		String str = "";
		if (id != null) {
			repository.deleteById(id);
			str = "Product Deleted";
		}
		return str;
	}

}
