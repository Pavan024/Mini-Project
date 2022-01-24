package com.ecommerce.customerservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.customerservice.entity.User;
import com.ecommerce.customerservice.header.HeaderGenerator;
import com.ecommerce.customerservice.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/** 
 * User Controller class consist of api's to register,login,reset password and update user
 * @author saipavan
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private HeaderGenerator headerGenerator;

	/** 
	 * This method is used for User Registration
	 * @param user
	 * @param request
	 * @return user instance
	 */
	@ApiOperation(value = "User Registration")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CREATED, response = User.class, message = "User registered Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Unable to register"),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response = String.class, message = "somthing went wrong") })
	@PostMapping(value = "/registration")
	public ResponseEntity<User> register(@Valid @RequestBody User user, HttpServletRequest request) {
		if (user != null)
			try {
				User newuser=userService.saveUser(user);
				return new ResponseEntity<User>(newuser,
						headerGenerator.getHeadersForSuccessPostMethod(request, newuser.getId()), HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}

	/** 
	 * This method is used for login by user 
	 * @param userNameOrmobileNumber
	 * @param password
	 * @return JWT Token to authorize
	 */
	@ApiOperation(value = "User Login")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "Logged In Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Invalid parameters") })
	@PostMapping("/login/{UserNameOrMobileNumber}/{password}")
	public ResponseEntity<String> login(@PathVariable("UserNameOrMobileNumber") String userNameOrmobileNumber,
			@PathVariable("password") String password) {
		String str = userService.login(userNameOrmobileNumber, password);
		if (!str.isEmpty()) {
			return new ResponseEntity<String>(str, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}
	
	/** 
	 * This method is used for reseting password 
	 * @param userNameOrmobileNumber
	 * @param password
	 * @param confirmPassword
	 * @return message
	 */
	@ApiOperation(value = "Reset Password By User")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "Password updated Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Invalid parameters") })
	@PutMapping("/password/reset/{UserNameOrMobileNumber}/{password}/{confirmPassword}")
	public ResponseEntity<String> resetPassword(@PathVariable("UserNameOrMobileNumber") String userNameOrmobileNumber,
			@PathVariable("password") String password, @PathVariable("confirmPassword") String confirmPassword) {
		String str = userService.resetPassword(userNameOrmobileNumber, password, confirmPassword);
		if (!str.isEmpty()) {
			return new ResponseEntity<String>(str, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}

	/** 
	 * This method for updating user details 
	 * @param user
	 * @param request
	 * @return user instance
	 */
	@ApiOperation(value = "Update User Details")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CREATED, response = User.class, message = "User Details Updated Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "unable to update user details"),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response = String.class, message = "somthing went wrong") })
	@PutMapping("/user/update")
	public ResponseEntity<User> update(@Valid@RequestBody User user,HttpServletRequest request) {
		if (user != null)
			try {
				User newUser = userService.update(user, user.getUserName());
				return new ResponseEntity<User>(newUser,
						headerGenerator.getHeadersForSuccessPostMethod(request, user.getId()), HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}
}
