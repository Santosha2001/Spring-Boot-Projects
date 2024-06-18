package com.scm.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    // @Autowired
    // private UserService userService;
    // private Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController() {
        super();
        System.out.println("UserController");
    }

    // user dash board
    @GetMapping("/dashboard")
    public String dashboard() {
        System.out.println("dashboard");
        return new String("/user/dashboard");
    }

    // user profile
    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        // Principle interface is to get the information about the user.

        return new String("/user/profile");
    }

    // add contacts
    // edit contacts
    // delete contacts
    // search contacts
    // view contacts
    // view contact details
}
