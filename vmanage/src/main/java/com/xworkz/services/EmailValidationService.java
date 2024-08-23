package com.xworkz.services;

public interface EmailValidationService {

    boolean saveOTPByEmailId(String email);

    boolean validateOPT(String email, String otp);
}
