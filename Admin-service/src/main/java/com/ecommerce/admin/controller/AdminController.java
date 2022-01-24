package com.ecommerce.admin.controller;

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

import com.ecommerce.admin.header.HeaderGenerator;
import com.ecommerce.admin.service.AdminService;
import com.ecommerce.admin.entity.User;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Admin Controller consist of api's to login, reset password and updating admin details
 */
@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private HeaderGenerator headerGenerator; 
	
	/**
	 * This method is used for Admin login
	 * @param UserNameOrMobileNumber
	 * @param password
	 * @return message
	 */	
	@ApiOperation(value = "Admin Login")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "Logged In Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Invalid parameters") })
	@PostMapping("/login/{userNameOrmobileNumber}/{password}")
	public ResponseEntity<String> login(@PathVariable("userNameOrmobileNumber") String userNameOrmobileNumber,
			@PathVariable("password") String password) {
		String str = adminService.login(userNameOrmobileNumber, password);
		if (!str.isEmpty()) {
			return new ResponseEntity<String>(str, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * This method is used for updating password by admin
	 * @param UserNameOrMobileNumber
	 * @param newPassword
	 * @param confirmPassword
	 * @return message
	 */
	@ApiOperation(value = "Reset Password By Admin")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, response = String.class, message = "Password updated Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, response = String.class, message = "Invalid parameters") })
	@PutMapping("/password/reset/{UserNameOrMobileNumber}/{password}/{confirmPassword}")
	public ResponseEntity<String> resetPassword(@PathVariable("UserNameOrMobileNumber") String userNameOrmobileNumber,
			@PathVariable("password") String password, @PathVariable("confirmPassword") String confirmPassword) {
		String str = adminService.resetPassword(userNameOrmobileNumber, password, confirmPassword);
		if (!str.isEmpty()) {
			return new ResponseEntity<String>(str, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * This method for updating admin details 
	 * @param new admin details
	 * @return updated admin details
	 */
	@ApiOperation(value = "Update Admin Details")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CREATED, response = User.class, message = "User Details Updated Successfully"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "unable to update user details"),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response = String.class, message = "somthing went wrong") })
	@PutMapping("/admin/update")
	public ResponseEntity<User> update(@Valid@RequestBody User user,HttpServletRequest request) {
		if (user != null)
			try {
				User newUser = adminService.update(user, user.getUserName());
				return new ResponseEntity<User>(newUser,
						headerGenerator.getHeadersForSuccessPostMethod(request, newUser.getId()), HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}
}
