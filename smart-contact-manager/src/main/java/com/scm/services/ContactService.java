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

    // get conatct by userId
    List<Contact> getContactsByUserId(String userId);

    Page<Contact> getByUser(User user, int page, int size, String sortBy, String order);

    // search contact
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,
            User user);
}
