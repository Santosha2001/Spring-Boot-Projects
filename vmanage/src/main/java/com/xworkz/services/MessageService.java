package com.xworkz.services;

public interface MessageService {

    boolean sendToAll(String messageContent, String subject);

    boolean sendToUser(String email, String messageContent, String subject);
}
