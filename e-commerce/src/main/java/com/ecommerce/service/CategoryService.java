package com.ecommerce.service;

import java.util.List;

import com.ecommerce.entities.Category;

public interface CategoryService {

	boolean saveCategory(Category category);

	Boolean isCategoryExistByName(String name);

	List<Category> getAllCategory();

	Boolean deleteCategory(int id);

	boolean isCategoryUpdated(Category category);

	Category findCategoryById(int id);

	List<Category> getAllActiveCategory();
}
