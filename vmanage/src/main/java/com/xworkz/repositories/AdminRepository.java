package com.xworkz.repositories;

import com.xworkz.entities.AdminEntity;

public interface AdminRepository {

    AdminEntity readByEmailId(String emailId);

    boolean updateOptById(int id, String otp);
}
