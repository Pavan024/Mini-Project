package com.ecommerce.admin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ecommerce.admin.entity.Seller;
import com.ecommerce.admin.header.HeaderGenerator;
import com.ecommerce.admin.service.SellerService;

@SpringBootTest
public class SellerControllerTest {

	@Mock
	private SellerService sellerService;

	@Mock
	private HeaderGenerator headerGenerator;

	@InjectMocks
	private SellerController sellerController;

	Seller seller = new Seller("", "Appario PVT LTD", "Hyderabad");

	@Test
	public void testAddSeller() {

		when(sellerService.addSeller(seller)).thenReturn(seller);

		ResponseEntity<Seller> response = sellerController.addSeller(seller, null);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(seller, response.getBody());

		ResponseEntity<Seller> res = sellerController.addSeller(null, null);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals(null, res.getBody());
	}

	@Test
	public void testGetAllSellers() {
		List<Seller> sellers = new ArrayList<Seller>();
		sellers.add(seller);

		when(sellerService.getAllSellers()).thenReturn(sellers);

		ResponseEntity<List<Seller>> response = sellerController.getAllSeller();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(sellers, response.getBody());
	}

	@Test
	public void testUpdateSeller() {
		
		when(sellerService.updateSeller(seller)).thenReturn(seller);

		ResponseEntity<Seller> response = sellerController.updateSeller(seller, null);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(seller, response.getBody());

		ResponseEntity<Seller> res = sellerController.updateSeller(null, null);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals(null, res.getBody());
	}
	
	@Test
	public void testDeleteSeller() {
		
		when(sellerService.deleteSeller(seller.getId())).thenReturn("Deleted");
		
		ResponseEntity<String> response = sellerController.deleteSeller(seller.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Deleted", response.getBody());
	}

}
