package com.ecommerce.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecommerce.admin.entity.User;
import com.ecommerce.admin.repository.AdminRepository;

@SpringBootTest
public class AdminServiceTest {

	@Mock
	private AdminRepository adminRepository;

	@InjectMocks
	private AdminService adminService;

	User user = new User("","Pavan123", "SaiPavan", "Kumar", "saipavan@gmail.com", "7996155024", "Hyderabad",
			"Pavan123", "admin");

	@Test
	public void testLogin() {

		when(adminRepository.findByMobileNumberAndRole(user.getMobileNumber(), user.getRole())).thenReturn(user);
		when(adminRepository.findByUserNameAndRole(user.getUserName(), user.getRole())).thenReturn(user);
		when(adminRepository.findByMobileNumberAndRole("1234567890", "admin")).thenReturn(user);
		when(adminRepository.findByUserNameAndRole("pavan123", "admin")).thenReturn(user);

		assertEquals("User Successfully Logged In", adminService.login(user.getUserName(), user.getPassword()));
		assertEquals("User Successfully Logged In", adminService.login(user.getMobileNumber(), user.getPassword()));

		assertEquals("Invalid username or password", adminService.login("pavan123", "pavan123"));
		assertEquals("Invalid username or password", adminService.login("1234567890", "pavan123"));
	}

	@Test
	public void testRestPassword() {

		when(adminRepository.findByMobileNumberAndRole(user.getMobileNumber(), user.getRole())).thenReturn(user);
		when(adminRepository.findByUserNameAndRole(user.getUserName(), user.getRole())).thenReturn(user);
		when(adminRepository.save(user)).thenReturn(user);
		when(adminRepository.findByMobileNumberAndRole("1234567890", "admin")).thenReturn(user);
		when(adminRepository.findByUserNameAndRole("pavan123", "admin")).thenReturn(user);

		assertEquals("sucessfully updated Password",
				adminService.resetPassword(user.getUserName(), user.getPassword(), "Pavan123"));
		assertEquals("sucessfully updated Password",
				adminService.resetPassword(user.getMobileNumber(), user.getPassword(), "Pavan123"));

		assertEquals("Password And confirm Password are not same",
				adminService.resetPassword("Pavan123", "Pavan123", "pavan123"));
		assertEquals("Password And confirm Password are not same",
				adminService.resetPassword("1234567890", "Pavan123", "pavan123"));
	}

	@Test
	public void testUpdate() {

		when(adminRepository.findByUserNameAndRole(user.getUserName(), user.getRole())).thenReturn(user);
		when(adminRepository.save(user)).thenReturn(user);

		assertEquals(user, adminService.update(user, user.getUserName()));
	}
}
