package com.ecommerce.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admin.entity.Order;
import com.ecommerce.admin.repository.OrderRepository;

/**
 * Order Service class
 * @author saipavan
 */
@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	/** 
	 * To get order details based on order id
	 * @param id
	 * @return order instance
	 */
	public Order getOrderById(String id) {
		return orderRepository.findById(id).orElse(null);
	}

	/** 
<<<<<<< HEAD
	 * To get order details of user based on user id
=======
	 * To get order details based on user id
>>>>>>> b12594fcac8e0895cca609828a2635ca46f44be9
	 * @param id
	 * @return List
	 */
	public List<Order> getAllOrdersById(String id) {
		return orderRepository.findAllOrdersByUserId(id);
	}

	/** 
	 * To get all the order details
	 * @return List
	 */
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

}