package com.xworkz.dto;

import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class EmailValidationDTO {

    @Id
    private int id;
    private String email;
    private String otp;
    private LocalDateTime createdAt;
}
