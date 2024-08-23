package com.xworkz.services.impl;

import com.xworkz.entities.MessageEntity;
import com.xworkz.entities.UserNotificationEntity;
import com.xworkz.entities.VendorEntity;
import com.xworkz.mail.MailSending;
import com.xworkz.repositories.MessageRepository;
import com.xworkz.repositories.UserNotificationRepository;
import com.xworkz.repositories.VendorRepository;
import com.xworkz.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private MailSending mailSending;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Override
    public boolean sendToAll(String messageContent, String subject) {
        System.out.println("messageContent=========" + messageContent);
        try {
            // Retrieve all vendors
            List<VendorEntity> allVendors = vendorRepository.readAllvendorDeta();
            System.out.println("allVendors================" + allVendors);
            // Iterate over each vendor
            for (VendorEntity vendorEntity : allVendors) {
                System.out.println("for each method");
                // Create a new message entity for each vendor
                MessageEntity messageEntity = new MessageEntity();
                messageEntity.setSenderId(vendorEntity.getId());
                messageEntity.setContent(messageContent);
                messageEntity.setTimestamp(LocalDateTime.now());

                // Save the message for the current vendor
                messageRepository.saveMessage(messageEntity);
                /*
                 * boolean sendMessageToEmail =
                 * mailSending.sendCustomEmail(vendorEntity.getOwnerName(),
                 * vendorEntity.getEmailId(), subject, messageContent);
                 */
                // Create and save a user notification for the current vendor and message
                UserNotificationEntity userNotification = new UserNotificationEntity();
                userNotification.setMessage(messageEntity);
                userNotification.setUserId(vendorEntity.getId());
                userNotification.setRead(false);
                userNotificationRepository.saveUserNotification(userNotification);
            }

            return true; // Successfully sent message to all vendors
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Failed to send message
        }
    }

    @Override
    public boolean sendToUser(String email, String messageContent, String subject) {
        if (email != null) {
            VendorEntity vendorEntity = vendorRepository.findVendorByEmail(email);
            System.out.println("sendToUser method");
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setSenderId(vendorEntity.getId());
            messageEntity.setContent(messageContent);
            messageEntity.setTimestamp(LocalDateTime.now());
            messageRepository.saveMessage(messageEntity);
            boolean sendMessageToEmail = mailSending.sendCustomEmail(vendorEntity.getOwnerName(),
                    vendorEntity.getEmailId(), subject, messageContent);
            if (sendMessageToEmail) {
                UserNotificationEntity userNotification = new UserNotificationEntity();
                userNotification.setMessage(messageEntity);
                userNotification.setUserId(vendorEntity.getId());
                userNotification.setRead(false);
                userNotificationRepository.saveUserNotification(userNotification);
                return true;
            }
        }
        return false;
    }
}
