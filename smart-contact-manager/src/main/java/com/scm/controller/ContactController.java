package com.scm.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helpers.AppConstants;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

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

    // contact add handler
    @PostMapping("/add_process")
    public String addContactHandler(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult,
            Authentication authentication, HttpSession session) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(errors -> logger.info(errors.toString()));
            session.setAttribute("message",
                    Message.builder().content("Please correct following errors.").type(MessageType.RED).build());
            return "user/add_contacts";
        }
        // Authenticate User
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        // image process
        logger.info("file information: {}", contactForm.getContactImage().getOriginalFilename());

        String fileName = UUID.randomUUID().toString();

        String contactImageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);

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
        contact.setCloudinaryImagePublicId(fileName);

        // set user to contact
        contact.setUser(user);

        contactService.saveContact(contact);

        System.out.println("Contact form -> " + contactForm);
        session.setAttribute("message", Message.builder().content("Contact saved.").type(MessageType.GREEN).build());
        return new String("redirect:/user/contacts/add");
    }

    // view contact
    @RequestMapping
    public String viewContact(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asec") String direction, Authentication authentication,
            Model model) {

        // load all the user contacts
        String emailOfLoggedInUser = Helper.getEmailOfLoggedInUser(authentication);
        User userByEmail = userService.getUserByEmail(emailOfLoggedInUser);

        Page<Contact> pageContact = contactService.getByUser(userByEmail, page, size, sortBy, direction);
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/contacts";
    }

    // search handler
    @RequestMapping("/search")
    public String searchHandler(

            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("mobile")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
                    direction, user);
        }

        logger.info("pageContact {}", pageContact);

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";
    }

    // delete handler
    @DeleteMapping("/delete/{id}")
    public String deleteContact(@PathVariable String id, HttpSession session) {

        contactService.deleteContact(id);
        logger.info("contactId {} deleted", id);
        session.setAttribute("message",
                Message.builder().content("Contact Deleted Succefully!").type(MessageType.GREEN).build());

        return "redirect:/user/contacts";

    }

    // load update contact
    @GetMapping("/edit/{id}")
    public String editContactView(@PathVariable String id, Model model, Authentication authentication) {

        Contact contact = contactService.getContactById(id);

        ContactForm contactForm = new ContactForm();

        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setMobile(contact.getMobile());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setFavourite(contact.isFavourite());
        contactForm.setImage(contact.getImage());
        // contactForm.setContactImage(null); // image not to be updated

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", id);

        return "user/update_contact";
    }

    // update contact
    @PostMapping("update/{id}")
    public String updateContact(@PathVariable String id, @Valid @ModelAttribute ContactForm contactForm,
            BindingResult bindingResult, Model model) {

        // update the contact
        if (bindingResult.hasErrors()) {
            return "user/update_contact";
        }

        var contact = contactService.getContactById(id);
        // Contact contact = new Contact();

        contact.setId(id);
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setMobile(contactForm.getMobile());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setFavourite(contactForm.isFavourite());
        // contact.setImage(contactForm.getImage());

        // process image:
        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file is not empty");

            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
            contact.setCloudinaryImagePublicId(fileName);
            contact.setImage(imageUrl);
            contactForm.setImage(imageUrl);

        } else {
            logger.info("file is empty");
        }

        var updatedContact = contactService.updateContact(contact);

        logger.info("updated contact {}", updatedContact);

        model.addAttribute("message", Message.builder().content("Contact updated.").type(MessageType.GREEN).build());

        return "redirect:/user/contacts/edit/" + id;
    }

}
