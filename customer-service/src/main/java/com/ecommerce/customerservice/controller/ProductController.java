package com.ecommerce.customerservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.customerservice.entity.Product;
import com.ecommerce.customerservice.header.HeaderGenerator;
import com.ecommerce.customerservice.service.ProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/** 
 * Product Controller class consist of api's retrive,search or products
 * @author saipavan
 */
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private HeaderGenerator headerGenerator;

	/** 
	 * To view all Products
	 * @return List
	 */
	@ApiOperation(value = "To view all Products")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = List.class, message = "view list Of Products"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Not Found") })
	@GetMapping(value = "/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		if (!products.isEmpty()) {
			return new ResponseEntity<List<Product>>(products, headerGenerator.getHeadersForSuccessGetMethod(),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<Product>>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}

	/** 
	 * searching a product by product name
	 * @param name
	 * @return List
	 */
	@ApiOperation(value = "Searching a product by name")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "Retrieved product details successfully"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "wrong parameters") })
	@GetMapping(value = "/product/{name}", params = "name")
	public ResponseEntity<List<Product>> getAllProductsByName(@RequestParam("name") String name) {
		List<Product> products = productService.getAllProductsByName(name);
		if (!products.isEmpty()) {
			return new ResponseEntity<List<Product>>(products, headerGenerator.getHeadersForSuccessGetMethod(),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<Product>>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}

	/** 
	 * searching a product by category
	 * @param categoryName
	 * @return List
	 */
	@ApiOperation(value = "Searching a product by category")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "Retrieved Products successfully"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Invalid parameters") })
	@GetMapping(value = "/product/{categoryName}", params = "categoryName")
	public ResponseEntity<List<Product>> getAllProductByCategory(@RequestParam("categoryName") String categoryName) {
		List<Product> products = productService.getAllProductByCategory(categoryName);
		if (!products.isEmpty()) {
			return new ResponseEntity<List<Product>>(products, headerGenerator.getHeadersForSuccessGetMethod(),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<Product>>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}

	/** 
	 * Getting a single product by using product id
	 * @param id
	 * @return product instance
	 */
	@ApiOperation(value = "getting a product by id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "Retrieved product details successfully"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Invalid parameters") })
	@GetMapping(value = "/product/{id}")
	public ResponseEntity<Product> getOneProductById(@PathVariable("id") String id) {
		Product product = productService.getProductById(id);
		if (product != null) {
			return new ResponseEntity<Product>(product, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
		}
		return new ResponseEntity<Product>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}
}
