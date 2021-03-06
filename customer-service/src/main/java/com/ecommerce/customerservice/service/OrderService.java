package com.ecommerce.customerservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.customerservice.entity.CartItems;
import com.ecommerce.customerservice.entity.Order;
import com.ecommerce.customerservice.entity.Product;
import com.ecommerce.customerservice.entity.User;
import com.ecommerce.customerservice.repository.OrderRepository;
import com.ecommerce.customerservice.repository.UserRepository;

/**
 * Order service class
 * 
 * @author saipavan
 */
@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserRepository userRepository;

	/**
	 * It will saves the order based on the user id
	 * 
	 * @param order
	 * @return order instance
	 */
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}

	/**
	 * This method is used to create order instance of user
	 * 
	 * @param user
	 * @return order instance
	 */
	public Order placeOrder(String userId) {
		User user = userRepository.getUserById(userId);
		Order order = new Order();
		List<Product> products = new ArrayList<Product>();
		List<CartItems> cartItems = cartService.getCartByUserId(user.getId());
		for (CartItems items : cartItems) {
			order.setUser(user);
			products.add(productService.getProductById(items.getProductId()));
			order.setTotalAmount(countTotalPrice(cartItems));
			order.setDate(LocalDate.now());
			order.setTypeOfTransaction("COD");
		}
		order.setProduct(products);
		return order;
	}

	/**
	 * This will be used to get an order based on the order id
	 * 
	 * @param id
	 * @return order instance
	 */
	public Order getOrderById(String id) {
		return orderRepository.findById(id).orElse(null);
	}

	/**
	 * This will be used to get all orders of a specific user
	 * 
	 * @param id
	 * @return List
	 */
	public List<Order> getAllOrdersById(String id) {
		return orderRepository.findAllOrdersByUserId(id);
	}

	/**
	 * This method is used to count the total amount of order based on product
	 * prices from cartItems
	 * 
	 * @param carts
	 * @return Total price of the order
	 */
	public float countTotalPrice(List<CartItems> carts) {
		float total = 0.0f;
		for (int i = 0; i < carts.size(); i++) {
			total = total + (carts.get(i).getSubTotal());
		}
		return total;
	}

	/**
	 * This method is used to update the product stocks after successful order
	 * 
	 * @param userId
	 */
	public void updateStock(String userId) {
		List<CartItems> cart = cartService.getCartByUserId(userId);
		for (CartItems item : cart) {
			productService.updateStock(item.getProductId(), item.getQuantity());
		}
	}
}
