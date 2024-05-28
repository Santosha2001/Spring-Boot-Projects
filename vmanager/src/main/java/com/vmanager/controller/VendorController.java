package com.vmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vmanager.entities.Vendor;
import com.vmanager.form.VendorForm;
import com.vmanager.helper.Message;
import com.vmanager.helper.MessageType;
import com.vmanager.service.VendorService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class VendorController {

    private Logger logger = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private VendorService vendorService;

    public VendorController() {
        logger.info(getClass().getSimpleName());
    }

    @GetMapping("/")
    public String indexHandler() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homeHandler() {
        return "index";
    }

    @GetMapping("/about")
    public String aboutHandler() {
        return "about";
    }

    @GetMapping("/services")
    public String contactHandler() {
        return "services";
    }

    @GetMapping("/login")
    public String loginHandler() {
        return "login";
    }

    // register handler
    @GetMapping("/register")
    public String registerHandler(Model model) {
        VendorForm vendorForm = new VendorForm();

        model.addAttribute("vendorForm", vendorForm);
        return new String("register");
    }

    // signup
    @PostMapping("/do-register")
    public String signupHandler(@Valid @ModelAttribute VendorForm vendorForm, BindingResult bindingResult,
            HttpSession session) {
        System.out.println("processing form.");
        System.out.println(vendorForm);

        // validation
        if (bindingResult.hasErrors()) {
            return "register";
        }

        Vendor user = new Vendor();
        user.setName(vendorForm.getName());
        user.setEmail(vendorForm.getEmail());
        user.setPassword(vendorForm.getPassword());
        user.setPhone(vendorForm.getPhone());
        user.setAddress(vendorForm.getAddress());
        user.setGst(vendorForm.getGst());

        System.out.println(user);

        // save user
        Vendor saveUser = vendorService.save(user);
        System.out.println("savedUser: " + saveUser);

        Message message = Message.builder().content("Registration Successful.").type(MessageType.GREEN).build();

        session.setAttribute("message", message);

        return "redirect:/register";
    }

}
