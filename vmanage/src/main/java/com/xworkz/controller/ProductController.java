package com.xworkz.controller;

import com.xworkz.dto.ProductDTO;
import com.xworkz.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    @ResponseBody
    public ResponseEntity<String> productSaveDTO(ProductDTO productDTO, Model model) {
        log.info("Attempting to save product: {}", productDTO);
        boolean saveProduct = this.productService.SaveProdctDTO(productDTO);
        if (saveProduct) {
            log.info("Product saved successfully: {}", productDTO);
            return ResponseEntity.ok().body("Product saved successfully");
        } else {
            log.warn("Failed to save product: {}", productDTO);
            return ResponseEntity.ok().body("Product not saved successfully");
        }
    }

    @GetMapping("/getProductDetailsByEmail")
    @ResponseBody
    public List<ProductDTO> getProductDetailsByEmail(@RequestParam String email) {
        log.info("Fetching product details for email: {}", email);
        List<ProductDTO> productDetails = productService.findProductDetails(email);
        log.info("Product details fetched: {}", productDetails);
        return productDetails;
    }

    @PostMapping("/productUpdate")
    @ResponseBody
    public ResponseEntity<String> productUpdate(ProductDTO productDTO, Model model) {
        log.info("Attempting to update product: {}", productDTO);
        boolean updateProduct = this.productService.updateProduct(productDTO);
        if (updateProduct) {
            log.info("Product updated successfully: {}", productDTO);
            return ResponseEntity.ok().body("Product updated successfully");
        } else {
            log.warn("Failed to update product: {}", productDTO);
            return ResponseEntity.ok().body("Product not updated successfully");
        }
    }
}
