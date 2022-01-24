package com.ecommerce.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.admin.entity.Category;

/**
 * Category Repository Interface Extends JpaRepository
 * @author saipavan
 */
@Repository
public interface CategoryRepository extends MongoRepository<Category, Long> {
	
	/**
	 * This used to get Category By Id
	 * @param id
	 * @return Category Instance
	 */
	Category getById(Long id);

}
