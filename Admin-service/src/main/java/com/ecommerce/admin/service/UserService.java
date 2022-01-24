package com.ecommerce.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admin.entity.User;
import com.ecommerce.admin.repository.UserRepository;

/** 
 * User Service class
 * @author saipavan
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository repository;
	
	/** 
	 * This method is used to get user instance by userName
	 * @param userName
	 * @return user instance
	 */
	public User getUser(String userName) {
		return repository.findByUserName(userName);
	}
	
	/** 
	 * This method is to get list of All users
	 * @return List
	 */
	public List<User> getAllUser() {
		return repository.findAll();
	}

}
