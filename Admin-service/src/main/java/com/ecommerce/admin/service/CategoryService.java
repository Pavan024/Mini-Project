package com.ecommerce.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admin.entity.Category;
import com.ecommerce.admin.repository.CategoryRepository;

/**
 * Category Service class
 * @author saipavan
 */
@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	/** 
	 * To view All categories
	 * @return List
	 */
	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	/** 
	 * To save a category in DB
	 * @param category
	 * @return Category Instance
	 */
	public Category addCategory(Category category) {
		return categoryRepository.save(category);
	}

	/** 
	 * To update a category details and save in DB
	 * @param category
	 * @param id
	 * @return category instance
	 */
	public Category updateCategory(Category category, String id) {
		Category newCategory = categoryRepository.findById(id).orElse(null);
		newCategory.setId(id);
		newCategory.setName(category.getName());
		newCategory.setDescription(category.getDescription());
		categoryRepository.save(newCategory);
		return newCategory;
	}

	/** 
	 * To delete a category from DB
	 * @param id
	 * @return message
	 */
	public String deleteCategory(String id) {
		String str = "";
		if (id != null) {
			categoryRepository.deleteById(id);
			str = "Category Deleted";
		}
		return str;
	}

}