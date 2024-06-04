package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    public ContactController(){
        System.out.println("ContactController");
    }

    @GetMapping("/add")
    public String addContactView(){
        return "user/add_contacts";
    }
    
}
