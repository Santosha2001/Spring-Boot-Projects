package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entities.Contact;
import com.scm.services.ContactService;

@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ContactService contactService;

    public ApiController(){
        System.out.println("ApiController");
    }

    // get contact
    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId){

        return contactService.getContactById(contactId);
    }

    // contacts view
    @GetMapping("/view_contact")
    public String getContacts() {
        return "user/view_contact";
    }

}
