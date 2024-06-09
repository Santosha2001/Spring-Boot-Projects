package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.ContactRepository;
import com.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact saveContact(Contact contact) {

        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);

        return contactRepository.save(contact);
    }

    @Override
    public Contact updateContact(Contact contact) {

        return contactRepository.save(contact);
    }

    @Override
    public void deleteContact(String id) {

        contactRepository.deleteById(id);
    }

    @Override
    public List<Contact> getAllContats() {

        return contactRepository.findAll();
    }

    @Override
    public Contact getContactById(String id) {

        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
    }

    @Override
    public List<Contact> searchContacts(String name, String email, String mobile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchContacts'");
    }

    @Override
    public List<Contact> getContactsByUserId(String userId) {

        return contactRepository.findByUserId(userId);
    }

}
