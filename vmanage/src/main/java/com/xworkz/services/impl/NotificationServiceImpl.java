package com.xworkz.services.impl;

import com.xworkz.dto.UserNotificationDTO;
import com.xworkz.entities.UserNotificationEntity;
import com.xworkz.repositories.UserNotificationRepository;
import com.xworkz.repositories.VendorRepository;
import com.xworkz.services.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public List<UserNotificationDTO> getNotifcationMessageByEmail(String email) {
        int vendorId = vendorRepository.findIdByEmail(email);
        List<UserNotificationEntity> entities = userNotificationRepository.getuserNotificationByEmail(vendorId);
        List<UserNotificationDTO> notificationDTOs = new ArrayList<>();
        for (UserNotificationEntity userNotificationEntity : entities) {
            if (userNotificationEntity.isRead() == false) {
                System.out.println(userNotificationEntity);
                UserNotificationDTO dto = new UserNotificationDTO();
                BeanUtils.copyProperties(userNotificationEntity, dto);
                notificationDTOs.add(dto);
            }

        }

        return notificationDTOs;
    }

    @Override
    public boolean markNotificationAsViewed(int id) {
        boolean read = true;
        boolean markView = userNotificationRepository.markNotificationAsViewed(id, read);
        if (markView) {
            return true;
        }
        return false;

    }
}
