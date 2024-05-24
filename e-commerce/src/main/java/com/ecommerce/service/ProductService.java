package com.ecommerce.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.entities.Product;

public interface ProductService {

	boolean saveProduct(Product product, MultipartFile file);

	List<Product> getAllProducts();

	boolean deleteProductById(int id);

	Product getProductById(int id);

	boolean updateProduct(Product product, MultipartFile file);

	List<Product> getAllActiveProducts(String category);
}
