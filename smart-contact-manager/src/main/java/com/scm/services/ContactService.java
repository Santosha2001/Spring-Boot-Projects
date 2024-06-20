package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {

    // add contact service
    Contact saveContact(Contact contact);

    // update contact service
    Contact updateContact(Contact contact);

    // delete contact service
    void deleteContact(String id);

    // get all contacts service
    List<Contact> getAllContats();

    // get contact by id service
    Contact getContactById(String id);

    // search contact by name, email, number
    List<Contact> searchContacts(String name, String email, String mobile);

    // get conatct by userId
    List<Contact> getContactsByUserId(String userId);

    Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction);
}
