package com.ecommerce.customerservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.customerservice.entity.CartItems;
import com.ecommerce.customerservice.header.HeaderGenerator;
import com.ecommerce.customerservice.service.CartService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/** 
 * Cart Controller consist of api's to add,retrive,update and remove product from cart
 * @author saipavan
 */
@RestController
public class CartController {

	@Autowired
	CartService cartService;

	@Autowired
	private HeaderGenerator headerGenerator;
	
	/** 
	 * To View All Products In Cart
	 * @param userId
	 * @return List
	 */
	@ApiOperation(value = "To View All Products In Cart")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = List.class, message = "List Of Products from cart"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Not Found") })
	@GetMapping(value = "/cart/{userId}")
	public ResponseEntity<List<CartItems>> getCart(@PathVariable("userId")String userId) {
		List<CartItems> cart = cartService.getCartByUserId(userId);
		if (!cart.isEmpty()) {
			return new ResponseEntity<List<CartItems>>(cart, headerGenerator.getHeadersForSuccessGetMethod(),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<CartItems>>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}

	/** 
	 * This method is used to add product to cart
	 * @param productId
	 * @param quantity
	 * @param userId
	 * @param request
	 * @return cartItems instance
	 */
	@ApiOperation(value = "Add Product To Cart")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CREATED, response = CartItems.class, message = "Product added to cart Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Unable to add product to cart"),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response = String.class, message = "somthing went wrong") })
	@PostMapping(value = "/cart/add", params = { "productId", "quantity", "userId" })
	public ResponseEntity<CartItems> addItemToCart(@RequestParam("productId") String productId,
			@RequestParam("quantity") int quantity, @RequestParam("userId") String userId, HttpServletRequest request) {
		CartItems cart = cartService.addItemToCart(productId, quantity, userId);
		if (cart != null) {
			return new ResponseEntity<CartItems>(cart,
					headerGenerator.getHeadersForSuccessPostMethod(request, cart.getId()), HttpStatus.CREATED);
		}
		return new ResponseEntity<CartItems>(headerGenerator.getHeadersForError(), HttpStatus.BAD_REQUEST);
	}

	/** 
	 * This method used to Update Quantity Of Product in cart 
	 * @param productId
	 * @param quantity
	 * @param id
	 * @param request
	 * @return cartItems instance
	 */
	@ApiOperation(value = "To Update Quantity Of Product")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = CartItems.class, message = "Updated quantity Of Product"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "unable to update quantity ") })
	@PutMapping(value = "/cart/update", params = { "productId", "quantity", "id" })
	public ResponseEntity<CartItems> updateQuantityInCart(@RequestParam("productId") String productId,
			@RequestParam("quantity") int quantity, @RequestParam("id") String id, HttpServletRequest request) {
		CartItems cart = cartService.updateCart(productId, quantity, id);
		if (cart != null) {
			return new ResponseEntity<CartItems>(cart,
					headerGenerator.getHeadersForSuccessPostMethod(request, cart.getId()), HttpStatus.CREATED);
		}
		return new ResponseEntity<CartItems>(headerGenerator.getHeadersForError(), HttpStatus.BAD_REQUEST);
	}

	/** 
	 * This method used to Remove Product From Cart 
	 * @param productId
	 * @param userId
	 * @return message
	 */
	@ApiOperation(value = "To Remove Product From Cart")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "Product removed from cart"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Unable to remove product from cart") })
	@DeleteMapping(value = "/cart/delete", params = { "productId", "userId" })
	public ResponseEntity<String> removeItemFromCart(@RequestParam("productId") String productId,
			@RequestParam("userId") String userId) {
		String str = cartService.deleteItemFromCart(productId, userId);
		if (str != null) {
			return new ResponseEntity<String>(str, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}
}
