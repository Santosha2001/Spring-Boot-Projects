package com.xworkz.services.impl;

import com.xworkz.entities.EmailValidationEntity;
import com.xworkz.mail.MailSending;
import com.xworkz.repositories.EmailIdValidationRepository;
import com.xworkz.services.EmailValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailValidationServiceImpl implements EmailValidationService {

    @Autowired
    private EmailIdValidationRepository emailIdValidationRepository;

    @Autowired
    private MailSending mailSending;

    @Override
    public boolean saveOTPByEmailId(String email) {
        if (email != null) {
            Random random = new Random();
            int generatedOtp = random.nextInt(900000) + 100000;
            String OTP = String.valueOf(generatedOtp);
            EmailValidationEntity entity = new EmailValidationEntity();
            entity.setEmail(email);
            entity.setOTP(OTP);
            entity.setOTPCreatedTime(LocalDateTime.now());

            boolean saveOTP = emailIdValidationRepository.saveOTPByEmailId(entity);
            if (saveOTP) {
                boolean sent = mailSending.sendEmailVerficationOTP(email, OTP);
                if (sent) {
                    return true;
                }

            }
        }

        return false;
    }

    @Override
    public boolean validateOPT(String email, String otp) {

        if (email != null && otp != null) {
            String latestotp = emailIdValidationRepository.findLatestOTPByEmailId(email);
            if (latestotp != null) {
                if (latestotp.equals(otp)) {
                    emailIdValidationRepository.deleteEmailVerificationDataByEmailId(email);
                    return true;
                }
            }

        }

        return false;
    }

}
