package com.xworkz.services;

import com.xworkz.dto.UserNotificationDTO;

import java.util.List;

public interface NotificationService {

    List<UserNotificationDTO> getNotifcationMessageByEmail(String email);

    boolean markNotificationAsViewed(int id);
}
