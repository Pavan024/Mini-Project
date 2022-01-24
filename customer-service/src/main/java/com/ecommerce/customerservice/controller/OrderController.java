package com.ecommerce.customerservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.customerservice.entity.Order;
import com.ecommerce.customerservice.entity.User;
import com.ecommerce.customerservice.header.HeaderGenerator;
import com.ecommerce.customerservice.service.CartService;
import com.ecommerce.customerservice.service.OrderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * In this order controller we can create an order and all the order based api's are available here
 * @author saipavan
 */
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;

	@Autowired
	private HeaderGenerator headerGenerator;

	/**
	 * It will creates an order based on specific user id
	 * 
	 * @param userId
	 * @param request
	 * @return order instance
	 */
	@PostMapping(value = "/order/{userId}")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CREATED, response = User.class, message = "Order created Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Invalid parameters"),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response = String.class, message = "somthing went wrong") })
	public ResponseEntity<Order> confirmOrder(@PathVariable("userId") String userId, HttpServletRequest request) {
		if (userId != null) {
			try {
				Order order = orderService.placeOrder(userId);
				Order newOrder = orderService.saveOrder(order);
				orderService.updateStock(userId);
				cartService.deleteCart(newOrder.getId(), userId);
				return new ResponseEntity<Order>(newOrder,
						headerGenerator.getHeadersForSuccessPostMethod(request, newOrder.getId()), HttpStatus.CREATED);
			} catch (Exception ex) {
				ex.printStackTrace();
				return new ResponseEntity<Order>(headerGenerator.getHeadersForError(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<Order>(headerGenerator.getHeadersForError(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Here we can get a receipt of order by using order id
	 * 
	 * @param id
	 * @return order instance
	 */
	@ApiOperation(value = "get a receipt of order by using order id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "Receipt retrieved successfully"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "wrong parameters") })
	@GetMapping(value = "/receipt/{id}")
	public ResponseEntity<Order> getOneOrderById(@PathVariable("id") String id) {
		Order order = orderService.getOrderById(id);
		if (order != null) {
			return new ResponseEntity<Order>(order, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
		}
		return new ResponseEntity<Order>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Here we can get orders of a specific user based on the user id.
	 * 
	 * @param userId
	 * @return List
	 */
	@ApiOperation(value = "getting orders of user by user id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "View orders of User"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "wrong parameters") })
	@GetMapping(value = "/orders/{userId}", params = "userId")
	public ResponseEntity<List<Order>> getAllOrdersByUserId(@RequestParam("userId") String userId) {
		List<Order> orders = orderService.getAllOrdersById(userId);
		if (!orders.isEmpty()) {
			return new ResponseEntity<List<Order>>(orders, headerGenerator.getHeadersForSuccessGetMethod(),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<Order>>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}
}
