package com.xworkz.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "admin_details")
@NamedQuery(name = "readByEmailId", query = "SELECT entity FROM AdminEntity entity WHERE entity.emailId = :emailId")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_Id")
    private int id;

    @Column(name = "admin_Name")
    private String adminName;
    @Column(name = "admin_emailId")
    private String emailId;

    @Column(name = "admin_password")
    private String password;

    @Column(name = "login_otp")
    private String otp;
}
