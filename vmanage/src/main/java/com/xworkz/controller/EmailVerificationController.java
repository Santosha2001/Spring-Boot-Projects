package com.xworkz.controller;

import com.xworkz.services.EmailValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@RequestMapping("/")
@EnableWebMvc
@Slf4j
public class EmailVerificationController {

    @Autowired
    private EmailValidationService emailValidationService;

    @GetMapping("/genrateOTPAndSave")
    public ResponseEntity<String> genrateOTPAndSave(@RequestParam String email) {
        log.info("Received request to generate OTP and save for email: {}", email);
        boolean emailExistsInDatabase = emailValidationService.saveOTPByEmailId(email);
        if (emailExistsInDatabase) {
            log.info("OTP generated and saved for email: {}", email);
            return ResponseEntity.ok("Exists");
        } else {
            log.warn("Email does not exist in the database: {}", email);
            return ResponseEntity.ok("not exists");
        }
    }

    @PostMapping("/validateEmailVerificationOTP")
    public ResponseEntity<String> validateOTP(@RequestParam String email, @RequestParam String otp) {
        log.info("Received request to validate OTP for email: {}", email);
        boolean validate = emailValidationService.validateOPT(email, otp);
        if (validate) {
            log.info("OTP validation successful for email: {}", email);
            return ResponseEntity.ok("valid");
        } else {
            log.warn("OTP validation failed for email: {}", email);
            return ResponseEntity.ok("not valid");
        }
    }
}
