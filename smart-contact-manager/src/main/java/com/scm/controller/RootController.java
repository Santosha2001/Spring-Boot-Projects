package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;

@ControllerAdvice
public class RootController {

    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    // user logged in information.
    // @ModelAttribute will add user information to all the handlers.
    @ModelAttribute
    public void loggedInUser(Authentication authentication, Model model) {

        if (authentication == null) {
            return;
        }
        System.out.println("Adding loggeed in user info to the model");
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}", username);

        User user = userService.getUserByEmail(username);
        System.out.println(user);

        System.out.println(user.getEmail());
        System.out.println(user.getName());
        model.addAttribute("loggedInUser", user);
    }
}
