package com.ecommerce.admin.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecommerce.admin.entity.Category;
import com.ecommerce.admin.repository.CategoryRepository;

@SpringBootTest
public class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryService categoryService;

	List<Category> categorys = new ArrayList<Category>();
	Category category = new Category("", "Electronics", "All Types of Electronic gadgets available");

	@Test
	public void testGetAllCategory() {
		categorys.add(category);

		when(categoryRepository.findAll()).thenReturn(categorys);
		assertEquals(categorys, categoryService.getAllCategory());
	}

	@Test
	public void testAddCategory() {

		when(categoryRepository.save(category)).thenReturn(category);
		assertEquals(category, categoryService.addCategory(category));
	}

	@Test
	public void testUpdateCategory() {
		
		when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
		when(categoryRepository.save(category)).thenReturn(category);

		assertTrue(category.equals(categoryService.updateCategory(category, category.getId())));
	}

	@Test
	public void testDeleteCategory() {

		assertEquals("Category Deleted", categoryService.deleteCategory(category.getId()));
		verify(categoryRepository, times(1)).deleteById(category.getId());
	}
}
