package com.ecommerce.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecommerce.admin.entity.Seller;
import com.ecommerce.admin.repository.SellerRepository;

@SpringBootTest
public class SellerServiceTest {

	@Mock
	private SellerRepository repository;

	@InjectMocks
	private SellerService sellerService;

	List<Seller> sellers = new ArrayList<Seller>();
	Seller seller = new Seller("", "Appario PVT LTD", "Hyderabad");

	@Test
	public void testAddSeller() {

		when(repository.save(seller)).thenReturn(seller);
		assertEquals(seller, sellerService.addSeller(seller));
	}

	@Test
	public void testGetAllSellers() {
		sellers.add(seller);

		when(repository.findAll()).thenReturn(sellers);
		assertEquals(sellers, sellerService.getAllSellers());
	}

	@Test
	public void testUpdateSeller() {
		sellers.add(seller);

		when(repository.findAll()).thenReturn(sellers);
		when(repository.save(seller)).thenReturn(seller);
		
		assertEquals(seller, sellerService.updateSeller(seller));
	}

	@Test
	public void testDeletSeller() {

		assertEquals("Seller Deleted", sellerService.deleteSeller(seller.getId()));
		verify(repository, times(1)).deleteById(seller.getId());
	}
}
