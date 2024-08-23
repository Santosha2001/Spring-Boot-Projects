package com.xworkz.dto;

import com.xworkz.entities.MessageEntity;
import lombok.Data;

import javax.persistence.Id;

@Data
public class UserNotificationDTO {

    @Id
    private int id;
    private int userId;
    private MessageEntity message;
    private boolean isRead;
}
