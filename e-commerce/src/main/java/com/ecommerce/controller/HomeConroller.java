package com.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.entities.Category;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeConroller {

	public HomeConroller() {
		log.info(getClass().getSimpleName() + " created.");
	}

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	// LOAD INDEX PAGE
	@GetMapping("/")
	public String home(Model model) {

		log.info("index page loaded.");
		model.addAttribute("title", "Home - ECommerce");
		return "index";
	}

	// LOAD REGISTER PAGE
	@GetMapping("/register")
	public String register(Model model) {

		log.info("register page loaded.");
		model.addAttribute("title", "Register - ECommerce");
		return "register";
	}

	// LOAD LOGIN PAGE
	@GetMapping("/login")
	public String login(Model model) {

		log.info("login page loaded.");
		model.addAttribute("title", "Login - ECommerce");
		return "login";
	}

	// LOAD PRODUCTS PAGE
	@GetMapping("/products")
	public String products(Model model, @RequestParam(value = "category", defaultValue = "") String category) {
		System.out.println("category: " + category);

		log.info("products page loaded.");
		model.addAttribute("title", "Products - ECommerce");

		List<Product> products = productService.getAllActiveProducts(category);
		List<Category> categories = categoryService.getAllActiveCategory();

		model.addAttribute("categories", categories);
		model.addAttribute("products", products);

		model.addAttribute("paramValue", category);

		return "products";
	}

	// LOAD PRODUCT PAGE
	@GetMapping("/product/{id}")
	public String product(Model model, @PathVariable int id) {
		Product productById = productService.getProductById(id);

		log.info("product page loaded.");
		model.addAttribute("title", "Product Details - ECommerce");
		model.addAttribute("product", productById);
		return "product_description";
	}

	// save user handler
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, @RequestParam("img") MultipartFile file, HttpSession session)
			throws IOException {

		boolean isUserSaved = userService.saveUser(user, file);
		if (isUserSaved) {
			System.out.println("saved");
			session.setAttribute("succMsg", "Registred successfuly.");
		} else {
			session.setAttribute("errorMsg", "Something went wrong.");
		}

		return "redirect:/register";
	}

}
