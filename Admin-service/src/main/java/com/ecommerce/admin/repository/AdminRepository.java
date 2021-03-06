package com.ecommerce.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.admin.entity.User;

/**
 * Admin Repository Interface extends MongoRepository
 * 
 * @author saipavan
 */
@Repository
public interface AdminRepository extends MongoRepository<User, String> {

	/**
	 * This will find user By MobileNumber
	 * 
	 * @param mobileNumber
	 * @param role
	 * @return User Instance
	 */
	User findByMobileNumberAndRole(String mobileNumber, String role);

	/**
	 * This will find User By UserName
	 * 
	 * @param userName
	 * @param role
	 * @return User Instance
	 */
	User findByUserNameAndRole(String userName, String role);

}
