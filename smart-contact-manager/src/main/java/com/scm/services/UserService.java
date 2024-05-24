package com.scm.services;

import java.util.List;
import java.util.Optional;

import com.scm.entities.User;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(Integer id);

    Optional<User> updateUser(User user);

    void deleteUser(Integer id);

    boolean isUserExist(Integer id);

    boolean isUserExistByEmail(String email);

    List<User> getAllUsers();
}
