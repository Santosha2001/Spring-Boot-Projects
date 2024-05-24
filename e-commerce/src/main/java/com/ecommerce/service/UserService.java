package com.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.entities.User;

public interface UserService {

	boolean saveUser(User user, MultipartFile file);
}
