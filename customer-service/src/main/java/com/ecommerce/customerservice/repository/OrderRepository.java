package com.ecommerce.customerservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.customerservice.entity.Order;

/**
 * Order Repository Interface extends MongoRepository
 * @author saipavan
 */
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
	
	/**
	 * This method is to get list of order By userId
	 * @param id
	 * @return List
	 */
	List<Order> findAllOrdersByUserId(String id);

}
