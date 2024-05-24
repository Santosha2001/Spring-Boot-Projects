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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.entities.Category;
import com.ecommerce.entities.Product;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.ProductService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	public AdminController() {

		log.info(getClass().getSimpleName() + " created.");
	}

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	/* admin home page handler */
	@GetMapping("/home")
	public String home(Model model) {

		log.info("admin home page loaded.");
		model.addAttribute("title", "Admin - ECommerce");

		return "admin/admin_home";
	}

	/* load category page handler */
	@GetMapping("/category")
	public String category(Model model) {

		log.info("category handler loaded.");
		model.addAttribute("title", "Product Category - ECommerce");
		model.addAttribute("category", categoryService.getAllCategory());

		return "admin/category";
	}

	// save category handler
	@PostMapping("/saveCategory")
	public String saveCategory(@ModelAttribute Category category, HttpSession session) throws IOException {

		boolean isCategorySaved = categoryService.saveCategory(category);

		if (!isCategorySaved) {
			session.setAttribute("errorMsg", "Not saved ! internal server error");
		} else {
			session.setAttribute("succMsg", "Saved successfully");
		}

		return "redirect:/admin/category";
	}

	// delete category
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable int id, HttpSession session) {

		Boolean deleteCategory = categoryService.deleteCategory(id);

		if (deleteCategory) {
			session.setAttribute("succMsg", "Category deleted.");
		} else {
			session.setAttribute("errorMsg", "Category not deleted.");
		}
		return "redirect:/admin/category";
	}

	// loadEditCategory
	@GetMapping("/loadEditCategory/{id}")
	public String loadEditCategory(@PathVariable int id, Model model) {

		model.addAttribute("title", "Edit Category - ECommerce");
		model.addAttribute("category", categoryService.findCategoryById(id));

		return "admin/edit_category";
	}

	// updateCategory
	@PostMapping("/updateCategory")
	public String updateCategory(@ModelAttribute Category category, Model model, HttpSession session)
			throws IOException {

		model.addAttribute("title", "Update Category - ECommerce");

		boolean isCcategoryUpdated = categoryService.isCategoryUpdated(category);
		if (isCcategoryUpdated) {
			session.setAttribute("succMsg", "Category updated.");
		} else {
			session.setAttribute("errorMsg", "Category not updated.");
		}

		return "redirect:/admin/loadEditCategory/" + category.getId();
	}

	/* load product handler */
	@GetMapping("/addProduct")
	public String addProduct(Model model) {
		model.addAttribute("title", "Add Product - ECommerce");

		List<Category> categories = categoryService.getAllCategory();
		model.addAttribute("categories", categories);

		return "admin/add_product";
	}

	// save product handler
	@PostMapping("/saveProduct")
	public String productAddHandler(@ModelAttribute Product product, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {

		boolean saveProduct = productService.saveProduct(product, file);
		if (saveProduct) {
			session.setAttribute("succMsg", "Product added..");
		} else {
			session.setAttribute("errorMsg", "Product not saved.");
		}
		return "redirect:/admin/addProduct";

	}

	// view product handler
	@GetMapping("/loadViewProduct")
	public String loadViewProduct(Model model) {
		model.addAttribute("title", "View Product - ECommerce");

		model.addAttribute("products", productService.getAllProducts());

		return "admin/view_product";
	}

	// delete product
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id, HttpSession session) {

		boolean deleteProduct = productService.deleteProductById(id);
		if (deleteProduct) {
			session.setAttribute("succMsg", "Product deleted.");
		} else {
			session.setAttribute("errorMsg", "Product not deleted.");
		}

		return "redirect:/admin/loadViewProduct";
	}

	// load edit product handler
	@GetMapping("/loadEditProduct/{id}")
	public String loadEditProduct(@PathVariable int id, Model model) {
		model.addAttribute("title", "Edit Product - ECommerce");

		model.addAttribute("product", productService.getProductById(id));
		model.addAttribute("categories", categoryService.getAllCategory());

		return "admin/edit_product";
	}

	// update product handler
	@PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file,
			HttpSession session) {

		boolean updateProduct = productService.updateProduct(product, file);

		if (product.getDiscount() < 0 || product.getDiscount() > 100) {
			session.setAttribute("errorMsg", "In-valid discount.");
		} else {
			if (updateProduct) {
				session.setAttribute("succMsg", "Product updated.");
			} else {
				session.setAttribute("errorMsg", "Product not updated.");
			}
		}

		return "redirect:/admin/loadEditProduct/" + product.getId();
	}

}
