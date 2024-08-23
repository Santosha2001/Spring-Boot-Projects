package com.xworkz.dto;

import com.xworkz.entities.ProductEntity;
import com.xworkz.entities.VendorEntity;
import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class OrderDTO {

    @Id
    int orderId;
    private int productId;
    private int vendorId;
    private String productName;
    private double productPrice;
    private double deliveryCharge;
    private String descriptionAboutProduct;
    private int orderQuantity;
    private LocalDateTime orderDate;
    private String deliveryDate;
    private String deliveryAddress;
    private String message;
    private String orderStatus;
    private ProductEntity product;
    private VendorEntity vendor;
    private double orderAmount;
    private double amountPaid;
    private String paymentStatus;
    private double totalAmountToPay;
    private double BalanceAmount;

    /* private String deliveryStatus; */
}
