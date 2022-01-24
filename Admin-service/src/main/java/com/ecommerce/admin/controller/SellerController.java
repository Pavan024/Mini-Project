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

import com.ecommerce.admin.entity.Seller;
import com.ecommerce.admin.header.HeaderGenerator;
import com.ecommerce.admin.service.SellerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Seller Controller consist of api's to add,retrieve,update and delete seller
 */
@RestController
public class SellerController {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private HeaderGenerator headerGenerator;
	
	/**This method is to add new Seller
	 * @param seller
	 * @param request
	 * @return seller Instance
	 */
	@ApiOperation(value = "Add Seller")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CREATED, response = Seller.class, message = "Seller Added Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Unable to add seller"),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response = String.class, message = "somthing went wrong") })
	@PostMapping("/seller/add")
	public ResponseEntity<Seller> addSeller(@RequestBody Seller seller, HttpServletRequest request) {
		Seller newSeller = sellerService.addSeller(seller);
		if (newSeller != null) {
			return new ResponseEntity<Seller>(newSeller,
					headerGenerator.getHeadersForSuccessPostMethod(request, newSeller.getId()), HttpStatus.CREATED);
		}
		return new ResponseEntity<Seller>(headerGenerator.getHeadersForError(), HttpStatus.BAD_REQUEST);
	}
	
	/** This method is to view All sellers
	 * @return List
	 */
	@ApiOperation(value = "View All Sellers")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "view All Sellers"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Not Found") })
	@GetMapping("/sellers")
	public ResponseEntity<List<Seller>> getAllSeller() {
		List<Seller> sellers = sellerService.getAllSellers();
		if (!sellers.isEmpty()) {
			return new ResponseEntity<List<Seller>>(sellers, headerGenerator.getHeadersForSuccessGetMethod(),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<Seller>>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}
	
	/** This method is to update Seller details
	 * @param seller
	 * @param request
	 * @return Seller Instance
	 */
	@ApiOperation(value = "Update Seller")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CREATED, response = Seller.class, message = "Seller Details updated Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Unable to update Seller details"),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response = String.class, message = "somthing went wrong") })
	@PutMapping("/seller/update")
	public ResponseEntity<Seller> updateSeller(@RequestBody Seller seller, HttpServletRequest request) {
		Seller newSeller = sellerService.updateSeller(seller);
		if (newSeller != null) {
			return new ResponseEntity<Seller>(newSeller,
					headerGenerator.getHeadersForSuccessPostMethod(request, newSeller.getId()), HttpStatus.CREATED);
		}
		return new ResponseEntity<Seller>(headerGenerator.getHeadersForError(), HttpStatus.BAD_REQUEST);
	}
	
	/** This method is to delete seller
	 * @param id
	 * @return message
	 */
	@ApiOperation(value = "Delete Seller")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "Seller Deleted Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Invalid parameters") })
	@DeleteMapping("/seller/delete/{id}")
	public ResponseEntity<String> deleteSeller(@PathVariable("id") String id) {
		String str = sellerService.deleteSeller(id);
		if (!str.isEmpty()) {
			return new ResponseEntity<String>(str, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}
}
