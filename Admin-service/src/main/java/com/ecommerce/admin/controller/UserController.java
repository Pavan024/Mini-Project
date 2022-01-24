package com.ecommerce.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.admin.entity.User;
import com.ecommerce.admin.header.HeaderGenerator;
import com.ecommerce.admin.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * User controller consists api's to retrive users
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private HeaderGenerator headerGenerator;
	
	/** This method is to View User By UserName
	 * @param userName
	 * @return User Instance
	 */
	@ApiOperation(value = "View User By UserName")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = User.class, message = "View User By UserName"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Not Found") })
	@GetMapping("/user/{userName}")
	public ResponseEntity<User> viewUser(@PathVariable("userName") String userName) {
		User user = userService.getUser(userName);
		if (user != null) {
			return new ResponseEntity<User>(user, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
		}
		return new ResponseEntity<User>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}
	
	/** View All Users
	 * @return List
	 */
	@ApiOperation(value = "View All Users")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = List.class, message = "View All Users"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Not Found") })
	@GetMapping("/users")
	public ResponseEntity<List<User>> viewAllUser() {
		List<User> users = userService.getAllUser();
		if (!users.isEmpty()) {
			return new ResponseEntity<List<User>>(users, headerGenerator.getHeadersForSuccessGetMethod(),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<User>>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}
}
