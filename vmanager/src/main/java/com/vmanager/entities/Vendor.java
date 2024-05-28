package com.vmanager.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Vendor {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String gst;
    private String createdBy;
    private String updatedBy;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    // private String website;
    private String image;
    private String role;

}
