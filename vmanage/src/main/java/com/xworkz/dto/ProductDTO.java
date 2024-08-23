package com.xworkz.dto;

import com.xworkz.entities.VendorEntity;
import lombok.Data;

import javax.persistence.Id;

@Data
public class ProductDTO {

    @Id
    private int id;
    private String email;
    private String category;
    private String productName;
    private String available;
    private double productPrice;
    private double deliveryCharge;
    private String descriptionAboutProduct;
    private String status;
    /*
     * private String orderQuantity; private String deliveryAddress; private String
     * deliveryDate;
     */
    private VendorEntity vendor;
}
