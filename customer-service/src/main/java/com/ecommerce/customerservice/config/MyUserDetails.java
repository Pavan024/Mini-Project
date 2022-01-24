package com.ecommerce.customerservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.customerservice.entity.User;
import com.ecommerce.customerservice.repository.UserRepository;

/**
 * User details class
 */
@Service
public class MyUserDetails implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		final User user = userRepository.findByUserName(userName);

		if (user == null) {
			throw new UsernameNotFoundException("User '" + userName + "' not found");
		}

		return org.springframework.security.core.userdetails.User//
				.withUsername(userName)//
				.password(user.getPassword())//
				.authorities(user.getRole()).accountExpired(false)//
				.accountLocked(false)//
				.credentialsExpired(false)//
				.disabled(false)//
				.build();
	}

}
