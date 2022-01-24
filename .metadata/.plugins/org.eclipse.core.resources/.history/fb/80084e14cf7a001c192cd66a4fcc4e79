package com.ecommerce.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.admin.entity.User;

/** 
 * User Repository Interface extends JpaRepository
 * @author saipavan
 */
@Repository
public interface UserRepository extends MongoRepository<User, Long> {
	
	/** 
	 * This method fetches user byuserName
	 * @param userName
	 * @return User Instance
	 */
	User findByUserName(String userName);

}
