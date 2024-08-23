package com.xworkz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {

    @Id
    private int id;
    private String adminName;
    private String emailId;
    private String password;
    private String otp;
}
