package com.ecommerce.customerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.customerservice.config.JwtTokenProvider;
import com.ecommerce.customerservice.entity.User;
import com.ecommerce.customerservice.exception.CustomException;
import com.ecommerce.customerservice.exception.UserAlreadyExistException;
import com.ecommerce.customerservice.repository.UserRepository;

/**
 * User Service Class
 * 
 * @author saipavan
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * By using this user will be saved
	 * 
	 * @param user
	 * @return user instance
	 */
	public User saveUser(User user) {
		if (emailExists(user.getEmailId())) {
			throw new UserAlreadyExistException("There is an account with that email address: " + user.getEmailId());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return user;
	}

	/**
	 * It will checks the entered email is already exist or not
	 * 
	 * @param emailId
	 * @return boolean value(true/false)
	 */
	public boolean emailExists(final String emailId) {
		return userRepository.findByEmailId(emailId) != null;
	}

	/**
	 * This is used to a user by using user id
	 * 
	 * @param userId
	 * @return user instance
	 */
	public User getUserById(String userId) {
		return userRepository.getUserById(userId);
	}

	/**
	 * This is used for user login
	 * 
	 * @param userNameOrmobileNumber
	 * @param password
	 * @return JWT Token
	 */
	public String login(String userNameOrmobileNumber, String password) {
		int count = 0;
		String token = "";
		boolean firstCase = false, secondCase = false;
		User user = new User();
		for (char ch : userNameOrmobileNumber.toCharArray()) {
			if (Character.isDigit(ch)) {
				count++;
			}
		}
		if (userNameOrmobileNumber.length() == 10 && count == 10) {
			user = userRepository.findByMobileNumber(userNameOrmobileNumber);
			firstCase = user.getMobileNumber().equals(userNameOrmobileNumber)
					&& passwordEncoder.matches(password, user.getPassword());
			userNameOrmobileNumber = user.getUserName();
		} else {
			user = userRepository.findByUserName(userNameOrmobileNumber);
			secondCase = user.getUserName().equals(userNameOrmobileNumber)
					&& passwordEncoder.matches(password, user.getPassword());
		}
		try {
			if (firstCase ^ secondCase)
				authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(userNameOrmobileNumber, password));
			token = jwtTokenProvider.createToken(userNameOrmobileNumber);
		} catch (CustomException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return token;
	}

	/**
	 * This method will fetch credentials form DB And Verify Password If password is
	 * correct it updates password and save in DB
	 * 
	 * @param userNameOrmobileNumber
	 * @param password
	 * @param confirmPassword
	 * @return message
	 */
	public String resetPassword(String userNameOrmobileNumber, String password, String confirmPassword) {
		int count = 0;
		for (char ch : userNameOrmobileNumber.toCharArray()) {
			if (Character.isDigit(ch)) {
				count++;
			}
		}
		if (userNameOrmobileNumber.length() == 10 && count == 10) {
			User user = userRepository.findByMobileNumber(userNameOrmobileNumber);
			if (password.equals(confirmPassword)) {
				user.setPassword(passwordEncoder.encode(confirmPassword));
				userRepository.save(user);
				return "Sucessfully updated Password";
			} else {
				return "";
			}
		} else {
			User user1 = userRepository.findByUserName(userNameOrmobileNumber);
			if (password.equals(confirmPassword)) {
				user1.setPassword(passwordEncoder.encode(confirmPassword));
				userRepository.save(user1);
				return "Sucessfully updated Password";
			} else {
				return "";
			}
		}
	}

	/**
	 * This method will fetch credentials form DB by userName And updates All user
	 * details from user instance and save in DB
	 * 
	 * @param user
	 * @param userName
	 * @return user instance
	 */
	public User update(User user, String userName) {
		User newuser = userRepository.findByUserName(userName);
		newuser.setFirstName(user.getFirstName());
		newuser.setLastName(user.getLastName());
		newuser.setAddress(user.getAddress());
		newuser.setMobileNumber(user.getMobileNumber());
		newuser.setEmailId(user.getEmailId());
		String str = passwordEncoder.encode(user.getPassword());
		newuser.setPassword(str);
		userRepository.save(newuser);
		return newuser;
	}
}
