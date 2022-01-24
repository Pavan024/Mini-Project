package com.ecommerce.customerservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.customerservice.entity.CartItems;

/**
 * Cart Repository Interface extends MongoRepository
 * 
 * @author saipavan
 */
@Repository
public interface CartRepository extends MongoRepository<CartItems, String> {

	/**
	 * This method is to get list of all cartItems of user By Id
	 * 
	 * @param userId
	 * @return List
	 */
	List<CartItems> findByUserId(String userId);

	/**
	 * This method is to remove cartItems of user after order in cartItems table
	 * 
	 * @param userId
	 * @return int
	 */
	@Query(value = "{'userId':?0}", delete = true)
	int deleteByUserId(String userId);
}