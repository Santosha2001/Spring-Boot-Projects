package com.xworkz.services;

import com.xworkz.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    boolean SaveProdctDTO(ProductDTO productDTO);

    List<ProductDTO> findProductDetails(String email);

    boolean updateProduct(ProductDTO EditProductDTO);

    List<ProductDTO> readAllProducts();
}
