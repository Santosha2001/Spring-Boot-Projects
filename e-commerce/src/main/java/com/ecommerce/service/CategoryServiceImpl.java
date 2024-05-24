package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ecommerce.entities.Category;
import com.ecommerce.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	public CategoryServiceImpl() {
		log.info(getClass().getSimpleName() + " created.");
	}

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public boolean saveCategory(Category category) {

		Boolean categoryExistByName = isCategoryExistByName(category.getName());

		if (categoryExistByName) {
			return false;
		} else {
			Category saveCategory = categoryRepository.save(category);

			if (!ObjectUtils.isEmpty(saveCategory)) {
				return true;
			} else
				return false;
		}

	}

	@Override
	public Boolean isCategoryExistByName(String name) {
		// TODO Auto-generated method stub
		return categoryRepository.existsByName(name);
	}

	@Override
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Boolean deleteCategory(int id) {

		Category category = categoryRepository.findById(id).orElse(null);

		if (!ObjectUtils.isEmpty(category)) {
			categoryRepository.delete(category);
			log.info("category with id: " + category.getId() + " deleted.");

			return true;
		}
		return false;
	}

	@Override
	public boolean isCategoryUpdated(Category category) {

		Category oldCategory = categoryRepository.findById(category.getId()).orElse(null);
		log.info("Category with id " + oldCategory.getId() + " edited.");

		if (!ObjectUtils.isEmpty(oldCategory)) {
			oldCategory.setName(category.getName());
			oldCategory.setIsActive(category.getIsActive());

		}

		boolean saveCategory = saveCategory(oldCategory);

		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}

		else {

			return true;

		}

	}

	@Override
	public Category findCategoryById(int id) {
		return categoryRepository.findById(id).orElse(null);

	}

	@Override
	public List<Category> getAllActiveCategory() {

		List<Category> categories = categoryRepository.findByIsActiveTrue();

		return categories;
	}

}
