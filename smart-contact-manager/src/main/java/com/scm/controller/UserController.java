package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {

    public UserController(){
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
    public String profile() {
        System.out.println("profile");
        return new String("/user/profile");
    }
    

    // add contacts
    // edit contacts
    // delete contacts
    // search contacts
    // view contacts
    // view contact details
}
