package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger =LoggerFactory.getLogger(getClass());

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    public ContactController() {
        System.out.println("ContactController");
    }

    @GetMapping("/add")
    public String addContactView(Model model) {

        ContactForm contactForm = new ContactForm();
        // contactForm.setName("Santosh");
        // contactForm.setFavourite(true);

        model.addAttribute("contactForm", contactForm);
        return "user/add_contacts";
    }

    // add contact
    @PostMapping("/add_process")
    public String addContactHandler(@Valid @ModelAttribute ContactForm contactForm,BindingResult bindingResult, Authentication authentication,HttpSession session) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(errors->logger.info(errors.toString()));
            session.setAttribute("message", Message.builder().content("Please correct following errors.").type(MessageType.RED).build());
            return "user/add_contacts";
        }
        // Authenticate User
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        // image process
        // logger.info("file information: {}",contactForm.getContactImage().getOriginalFilename());

        String contactImageUrl=imageService.uploadImage(contactForm.getContactImage());

        // convert ContactForm -> Contact
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setMobile(contactForm.getMobile());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setFavourite(contactForm.isFavourite());
        contact.setImage(contactImageUrl);

        // set user to contact
        contact.setUser(user);

        // contactService.saveContact(contact);

        System.out.println("Contact form -> " + contactForm);
        session.setAttribute("message", Message.builder().content("Contact saved.").type(MessageType.GREEN).build());
        return new String("redirect:/user/contacts/add");
    }

}
