package com.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.entities.Product;
import com.ecommerce.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

	public ProductServiceImpl() {
		log.info(getClass().getSimpleName() + " created.");
	}

	@Autowired
	private ProductRepository productRepository;

	@Override
	public boolean saveProduct(Product product, MultipartFile file) {

		String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
		product.setImageName(imageName);
		product.setDiscount(0);
		product.setDiscountPrice(product.getPrice());

		if (!ObjectUtils.isEmpty(product)) {

			File saveFile = null;
			try {
				saveFile = new ClassPathResource("static/images").getFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product" + File.separator
					+ file.getOriginalFilename());

			// System.out.println(path);
			log.info("path to image save: " + path);

			try {
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}

			productRepository.save(product);
			log.info("product with id: " + product.getId() + " saved");

			return true;
		}

		return false;
	}

	@Override
	public List<Product> getAllProducts() {

		return productRepository.findAll();
	}

	@Override
	public boolean deleteProductById(int id) {
		Product product = productRepository.findById(id).orElse(null);

		if (!ObjectUtils.isEmpty(product)) {
			productRepository.delete(product);
			log.info("Product with id " + id + " deleted.");
			return true;
		}
		return false;
	}

	@Override
	public Product getProductById(int id) {
		Product product = productRepository.findById(id).orElse(null);
		return product;
	}

	@Override
	public boolean updateProduct(Product product, MultipartFile file) {

		Product productById = getProductById(product.getId());

		String imageName = file.isEmpty() ? productById.getImageName() : file.getOriginalFilename();

		productById.setTitle(product.getTitle());
		productById.setDescription(product.getDescription());
		productById.setCategory(product.getCategory());
		productById.setPrice(product.getPrice());
		productById.setStock(product.getStock());
		productById.setImageName(imageName);
		productById.setIsActive(product.getIsActive());

		productById.setDiscount(product.getDiscount());

		Double discount = product.getPrice() * (product.getDiscount() / 100.0);
		Double discountPrice = product.getPrice() - discount;

		productById.setDiscountPrice(discountPrice);

		Product updateProduct = productRepository.save(productById);

		if (!ObjectUtils.isEmpty(updateProduct)) {

			if (!file.isEmpty()) {
				File saveFile = null;
				try {
					saveFile = new ClassPathResource("static/images").getFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product" + File.separator
						+ file.getOriginalFilename());

				log.info("path where img updated: " + path);
				try {
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			log.info("Product updated.");
			return true;
		}

		return false;
	}

	@Override
	public List<Product> getAllActiveProducts(String category) {
		List<Product> isActiveTrue = new ArrayList<>();
		if (ObjectUtils.isEmpty(category)) {
			isActiveTrue = productRepository.findByIsActiveTrue();
		} else {
			isActiveTrue = productRepository.findByCategory(category);
		}

		return isActiveTrue;
	}

}
