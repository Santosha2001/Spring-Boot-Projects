package com.xworkz.controller;

import com.xworkz.services.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/sendMessage")
    @ResponseBody
    public String sendMessage(@RequestParam("subject") String subject, @RequestParam("message") String message,
                              @RequestParam("sendToAll") boolean sendToAll,
                              @RequestParam(value = "recipientEmail", required = false) String recipientEmail) {
        log.info("Received request to send message with subject: {}, sendToAll: {}, recipientEmail: {}", subject, sendToAll, recipientEmail);

        try {
            if (sendToAll) {
                log.debug("Sending message to all users. Subject: {}, Message: {}", subject, message);
                boolean sendMessageToAll = messageService.sendToAll(message, subject);
                if (sendMessageToAll) {
                    log.info("Message sent to all users successfully.");
                    return "Message sent successfully";
                } else {
                    log.warn("Failed to send message to all users.");
                    return "!Message sent successfully";
                }
            } else {
                log.debug("Sending message to user: {}. Subject: {}, Message: {}", recipientEmail, subject, message);
                boolean messageSent = messageService.sendToUser(recipientEmail, message, subject);
                if (messageSent) {
                    log.info("Message sent to user: {} successfully.", recipientEmail);
                    return "Message sent successfully";
                } else {
                    log.warn("Failed to send message to user: {}.", recipientEmail);
                }
            }
        } catch (Exception e) {
            log.error("Failed to send message due to an exception: ", e);
            return "Failed to send message: " + e.getMessage();
        }

        log.warn("Message sending was unsuccessful for unspecified reasons.");
        return "!Message sent successfully";
    }
}
