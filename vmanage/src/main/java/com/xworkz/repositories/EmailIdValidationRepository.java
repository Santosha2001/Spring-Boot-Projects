package com.xworkz.repositories;

import com.xworkz.entities.EmailValidationEntity;

public interface EmailIdValidationRepository {

    boolean saveOTPByEmailId(EmailValidationEntity entity);

    String findLatestOTPByEmailId(String email);

    boolean deleteEmailVerificationDataByEmailId(String email);
}
