package com.xworkz.repositories;

import com.xworkz.entities.UserNotificationEntity;

import java.util.List;

public interface UserNotificationRepository {

    boolean saveUserNotification(UserNotificationEntity userNotificationEntity);

    List<UserNotificationEntity> getuserNotificationByEmail(int vendorId);

    boolean markNotificationAsViewed(int notificationId, boolean read);

    UserNotificationEntity findById(int id);

    List<UserNotificationEntity> readall();
}
