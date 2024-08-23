package com.xworkz.repositories;

import com.xworkz.entities.MessageEntity;

import java.util.List;

public interface MessageRepository {

    boolean saveMessage(MessageEntity messageEntity);

    List<MessageEntity> readAllMessages();

    boolean saveMessagePersonlly(MessageEntity messageEntity);
}
