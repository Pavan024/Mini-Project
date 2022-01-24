package com.ecommerce.customerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.customerservice.entity.User;

/** 
 * User Repository Interface extends JpaRepository
 * @author saipavan
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

	/** 
	 * Finds User from DB by userName and returns user instance
	 * @param userName
	 * @return user instance
	 */
	User findByUserName(String userName);

	/** 
	 * Finds User from DB by mobile number and returns user instance
	 * @param mobileNumber
	 * @return user instance
	 */
	User findByMobileNumber(String mobileNumber);
	
	/**
	 * This method to retrive user by userId
	 * @param userId
	 * @return user instance
	 */
	User getUserById(String userId);
	
	/**
	 * This method to retrie user by EmailId
	 * @param email
	 * @return user instance
	 */
	User findByEmailId(String email);
	
	/**
	 * This method is to check user exists by username or not
	 * @param userName
	 * @return boolean value(true/false)
	 */
	boolean existsByUserName(String userName);

}
