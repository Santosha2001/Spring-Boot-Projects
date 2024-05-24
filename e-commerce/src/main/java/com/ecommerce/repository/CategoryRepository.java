package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Boolean existsByName(String name);
	
	List<Category> findByIsActiveTrue();
}
