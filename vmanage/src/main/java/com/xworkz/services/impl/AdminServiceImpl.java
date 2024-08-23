package com.xworkz.services.impl;

import com.xworkz.entities.AdminEntity;
import com.xworkz.mail.MailSending;
import com.xworkz.repositories.AdminRepository;
import com.xworkz.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MailSending mailSending;

    @Override
    public boolean readByEmailId(String emailId) {
        return false;
    }

    @Override
    public boolean generateAndSendOTP(String email) {
        AdminEntity entity = adminRepository.readByEmailId(email);

        if (entity == null || entity.getEmailId() == null) {
            return false;
        }
        String otp = generateOTP();
        boolean save = adminRepository.updateOptById(entity.getId(), otp);
        if (save) {
            boolean sent = mailSending.sendOTPAdminEmail(entity.getEmailId(), otp, entity.getAdminName());
            if (sent) {
                return true;
            }
        }
        return false;
    }

    private String generateOTP() {
        Random random = new Random();
        int otpNumber = 100000 + random.nextInt(900000);
        return String.valueOf(otpNumber);
    }

    @Override
    public boolean validateOpt(String email, String otp) {
        AdminEntity entity = adminRepository.readByEmailId(email);

        if (entity == null || entity.getEmailId() == null) {
            return false;
        }

        if (entity.getEmailId().equals(email) && entity.getOtp().equals(otp)) {
            boolean resetOtp = adminRepository.updateOptById(entity.getId(), null);
            if (resetOtp) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean adminLogin(String email, String password) {
        if (email != null && password != null) {
            AdminEntity entity = adminRepository.readByEmailId(email);
            if (entity != null) {
                if (entity.getEmailId().equals(email) && entity.getPassword().equals(password)) {
                    return true;
                }

            }

        }

        return false;
    }
}
