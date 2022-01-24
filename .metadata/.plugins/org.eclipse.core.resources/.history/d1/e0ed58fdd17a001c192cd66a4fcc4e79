package com.ecommerce.admin.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.admin.entity.Category;

/**
 * Category Repository Interface Extends JpaRepository
 * @author saipavan
 */
@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

	void deleteById(String id);

	Optional<Category> findById(String id);

}
