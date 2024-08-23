package com.xworkz.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "email_validation")
@NamedQuery(name = "findLatestOtpByEmail", query = "SELECT entity.OTP FROM EmailValidationEntity entity WHERE entity.email = :email ORDER BY entity.OTPCreatedTime DESC")
@NamedQuery(name = "deleteEmailVerificationDataByEmail", query = "DELETE FROM EmailValidationEntity entity WHERE entity.email = :email")
public class EmailValidationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "validation_emailId")
    private String email;

    @Column(name = "validation_OTP")
    private String OTP;

    @Column(name = "OTP_Created_Time")
    private LocalDateTime OTPCreatedTime;
}
