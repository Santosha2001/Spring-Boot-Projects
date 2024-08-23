package com.xworkz.repositories;

import com.xworkz.entities.ProductEntity;

import java.util.List;

public interface ProductRepository {

    boolean saveProdctEntity(ProductEntity productEntity);

    List<ProductEntity> findProductByEmail(int id);

    ProductEntity getProductDetailesByProductID(int id);

    boolean updateProductById(int id, ProductEntity productEntity);

    List<ProductEntity> readAllProducts();

    /* boolean updateOrderProduct(int id, String status); */
}
