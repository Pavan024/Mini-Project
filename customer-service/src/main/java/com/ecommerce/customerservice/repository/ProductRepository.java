package com.ecommerce.customerservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.customerservice.entity.Product;

/**
 * Product Repository Interface extends JpaRepository
 * @author saipavan
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
	
	/**
	 * This method is get Products by name
	 * @param name
	 * @return List
	 */
	List<Product> findByName(String name);
	
	/**
	 * This method is to get products by categoryName
	 * @param categoryName
	 * @return List
	 */
	List<Product> findAllByCategoryName(String categoryName);

}
