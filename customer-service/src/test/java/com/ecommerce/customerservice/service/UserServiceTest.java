package com.ecommerce.customerservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecommerce.customerservice.config.JwtTokenProvider;
import com.ecommerce.customerservice.entity.User;
import com.ecommerce.customerservice.repository.UserRepository;

@WebMvcTest(UserService.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private  PasswordEncoder passwordEncoder;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	User user = new User("", "Pavan123", "SaiPavan", "Kumar", "saipavan@gmail.com", "7996155024", "Hyderabad",
			"Pavan123", "user");

	@Test
	public void testSaveUser() {
		
		when(userRepository.save(user)).thenReturn(user);
		when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());

		assertEquals(false, userService.emailExists(user.getEmailId()));
		assertEquals(user, userService.saveUser(user));
	}

	@Test
	public void testEmailExists() {

		when(userRepository.findByEmailId(user.getEmailId())).thenReturn(user);
		assertEquals(true, userService.emailExists(user.getEmailId()));
	}

	@Test
	public void testGetUserById() {

		when(userRepository.getUserById(user.getId())).thenReturn(user);
		assertEquals(user, userService.getUserById(user.getId()));
	}

	@Test
	public void testLogin() {

		when(userRepository.findByMobileNumber(user.getMobileNumber())).thenReturn(user);
		when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
		when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);
		when(authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())))
						.thenReturn(null);
		when(jwtTokenProvider.createToken(user.getUserName())).thenReturn("token");
		
//		assertEquals("", userService.login(user.getUserName(), "pavan321"));
//		assertEquals("", userService.login(user.getMobileNumber(), "pavan321"));

		assertEquals("token", userService.login(user.getUserName(), user.getPassword()));
		assertEquals("token", userService.login(user.getMobileNumber(), user.getPassword()));
	}

	@Test
	public void testResetPassword() {

		when(userRepository.findByMobileNumber(user.getMobileNumber())).thenReturn(user);
		when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
		when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
		when(userRepository.save(user)).thenReturn(user);

		assertEquals("Sucessfully updated Password",
				userService.resetPassword(user.getMobileNumber(), user.getPassword(), user.getPassword()));
		assertEquals("Sucessfully updated Password",
				userService.resetPassword(user.getUserName(), user.getPassword(), user.getPassword()));

		assertEquals("", userService.resetPassword(user.getMobileNumber(), user.getPassword(), "pavan321"));
		assertEquals("", userService.resetPassword(user.getUserName(), user.getPassword(), "pavan321"));
	}

	@Test
	public void testUpdate() {

		when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
		when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
		when(userRepository.save(user)).thenReturn(user);

		assertEquals(user, userService.update(user, user.getUserName()));
	}
}