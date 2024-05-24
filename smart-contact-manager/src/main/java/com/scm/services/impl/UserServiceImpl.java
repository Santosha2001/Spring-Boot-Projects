package com.scm.services.impl;

import java.util.List;
import java.util.Optional;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.UserRepository;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {

        // generate user id
        // Integer userId = UUID.randomUUID();
        // user.setId(userId);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Integer id) {

        return userRepository.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {

        User updateUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        // updateUser by user
        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(user.getPassword());
        updateUser.setAbout(user.getAbout());
        updateUser.setImage(user.getImage());
        updateUser.setMobile(user.getMobile());
        updateUser.setEnabled(user.isEnabled());
        updateUser.setProvider(user.getProvider());
        updateUser.setEmailVerified(user.isEmailVerified());
        updateUser.setMobileVerified(user.isMobileVerified());
        updateUser.setProviderId(user.getProviderId());

        User saveUpdatedUser = userRepository.save(updateUser);

        return Optional.ofNullable(saveUpdatedUser);

    }

    @Override
    public void deleteUser(Integer id) {
        User deleteUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        userRepository.delete(deleteUser);
    }

    @Override
    public boolean isUserExist(Integer id) {

        User isExist = userRepository.findById(id).orElse(null);

        return isExist != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}