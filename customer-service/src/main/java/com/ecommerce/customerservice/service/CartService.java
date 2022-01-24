package com.ecommerce.customerservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.customerservice.entity.CartItems;
import com.ecommerce.customerservice.entity.Product;
import com.ecommerce.customerservice.repository.CartRepository;
import com.ecommerce.customerservice.repository.ProductRepository;

/**
 * Cart Service Class
 * @author saipavan
 */
@Service
@Transactional
public class CartService {

	@Autowired
	private CartRepository repository;

	@Autowired
	private ProductRepository productRepository;

	/**
	 * Returns list of all cart items form DB
	 * @return List
	 */
	public List<CartItems> getAllItemsFromCart() {
		return repository.findAll();
	}

	/**
	 * This method is used to add product into cart
	 * @param productId
	 * @param quantity
	 * @param userId
	 * @return cartItems instance
	 */
	public CartItems addItemToCart(String productId, int quantity, String userId) {
		CartItems item = new CartItems();
		Product product = productRepository.findById(productId).orElse(null);
		float sub_total = getSubTotalForItem(product, quantity);
		if (product.getStock() >= quantity) {
			item.setProductId(productId);
			item.setQuantity(quantity);
			item.setUserId(userId);
			item.setSubTotal(sub_total);
			repository.save(item);
		} else {
			item = null;
		}
		return item;

	}

	/**
	 * This method is used to check product exist in cart or not
	 * @param productId
	 * @return boolean value(true/false)
	 */
	public boolean checkIfItemIsExist(String productId) {
		List<CartItems> cart = getAllItemsFromCart();
		for (CartItems item : cart) {
			if (item.getProductId().equals(productId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is used to change product quantity in cart 
	 * @param productId
	 * @param quantity
	 * @param id
	 */
	public void changeItemQuantity(String productId, Integer quantity, String id) {
		CartItems item = repository.findById(id).orElse(null);
		if (item.getProductId().equals(productId)) {
			item.setQuantity(quantity);
		}
	}

	/**
	 * This method is used to remove product from cart
	 * @param productId
	 * @param userId
	 * @return message
	 */
	public String deleteItemFromCart(String productId, String userId) {
		String str = null;
		List<CartItems> cart = repository.findByUserId(userId);
		for (CartItems item : cart) {
			if (item.getProductId().equals(productId)) {
				repository.deleteById(item.getId());
				str = "Product Removed From Cart";
			}
		}
		return str;
	}

	/**
	 * Returns list of all cart items form DB of user
	 * @param userId
	 * @return List
	 */
	public List<CartItems> getCartByUserId(String userId) {
		return repository.findByUserId(userId);
	}

	/**
	 * Deletes Cart by cart id
	 * @param id
	 * @param userId
	 */
	public void deleteCart(String orderId, String userId) {
//		remove(orderId);
		repository.deleteByUserId(userId);
	}

//	/**
//	 * This method remove cartItems after order in mapped table
//	 * @param id
//	 * @return
//	 */
//	public int remove(String id) {
//		return repository.deleteById(id);
//	}

	/**
	 * This method is used to update cart
	 * @param productId
	 * @param quantity
	 * @param id
	 * @return cartItems instance
	 */
	public CartItems updateCart(String productId, int quantity, String id) {
		CartItems item = repository.findById(id).orElse(null);
		if (checkIfItemIsExist(productId)) {
//			changeItemQuantity(productId, quantity, id);
			item.setQuantity(quantity);
			Product product = productRepository.findById(productId).orElse(null);
			item.setSubTotal(getSubTotalForItem(product, quantity));
			repository.save(item);
		}
		return item;
	}

	/**
	 * This method is used to calculate total amount of each product in cart
	 * @param product
	 * @param quantity
	 * @return subTotal price of product
	 */
	public float getSubTotalForItem(Product product, int quantity) {
		return quantity * product.getPrice();
	}

}