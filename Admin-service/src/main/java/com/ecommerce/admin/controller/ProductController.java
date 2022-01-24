package com.ecommerce.admin.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.admin.entity.Product;
import com.ecommerce.admin.header.HeaderGenerator;
import com.ecommerce.admin.service.ProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Product Controller consists of api's to add,retrieve,update and delete product
 */
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private HeaderGenerator headerGenerator;
	
	/**
	 * This method is to add new Product
	 * @param product
	 * @param request
	 * @return product Instance
	 */
	@ApiOperation(value="To Add New Product")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CREATED, response = Product.class, message = "Product Added Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Unable to add product"),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response = String.class, message = "somthing went wrong") })
	@PostMapping("/product/add")
	public ResponseEntity<Product> addProduct(@RequestBody Product product, HttpServletRequest request) {
		Product newproduct = productService.addProduct(product);
		if (newproduct != null) {
			return new ResponseEntity<Product>(newproduct,
					headerGenerator.getHeadersForSuccessPostMethod(request, newproduct.getId()), HttpStatus.CREATED);
		}
		return new ResponseEntity<Product>(headerGenerator.getHeadersForError(), HttpStatus.BAD_REQUEST);
	}
	
	/**This method is to update product details
	 * @param product
	 * @param request
	 * @return Product Instance
	 */
	@ApiOperation(value="To Update Product Details")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CREATED, response = Product.class, message = "Product Details updated Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Unable to update product details"),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response = String.class, message = "somthing went wrong") })
	@PutMapping("/product/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, HttpServletRequest request) {
		Product newproduct = productService.updateProduct(product);
		if (newproduct != null) {
			return new ResponseEntity<Product>(newproduct,
					headerGenerator.getHeadersForSuccessPostMethod(request, newproduct.getId()), HttpStatus.CREATED);
		}
		return new ResponseEntity<Product>(headerGenerator.getHeadersForError(), HttpStatus.BAD_REQUEST);
	}
	
	/**This method is to delete Product
	 * @param id
	 * @return message
	 */
	@ApiOperation(value="To Delete Product")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "Product Deleted Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "unable to delete product") })
	@DeleteMapping("/product/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") String id) {
		String str = productService.deleteProduct(id);
		if (!str.isEmpty()) {
			return new ResponseEntity<String>(str, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}
	
	/**To View All Products
	 * @return List
	 */
	@ApiOperation(value="To View All Products")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "view All Products"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Not Found") })
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProduct();
		if (!products.isEmpty()) {
			return new ResponseEntity<List<Product>>(products, headerGenerator.getHeadersForSuccessGetMethod(),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<Product>>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}
}
