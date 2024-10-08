package com.xworkz.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product_details")
@NamedQuery(name = "findProductByVendor", query = "SELECT p FROM ProductEntity p WHERE p.vendor.id = :vendorId")
@NamedQuery(name = "getProductDetailesByProductID", query = "SELECT p FROM ProductEntity p WHERE p.id= :ProductID")
@NamedQuery(name = "readAllProducts", query = "SELECT p FROM ProductEntity p")

public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;

    private String category;

    private String productName;

    private String available;

    private double productPrice;

    private double deliveryCharge;

    /* private String status; */

    private String descriptionAboutProduct;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id")
    private VendorEntity vendor;
}
