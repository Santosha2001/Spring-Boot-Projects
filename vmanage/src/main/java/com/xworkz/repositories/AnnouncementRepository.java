package com.xworkz.repositories;

import com.xworkz.entities.AnnouncementEntity;

import java.util.List;

public interface AnnouncementRepository {

    boolean saveAnnouncement(AnnouncementEntity announcementEntity);

    List<AnnouncementEntity> getAllAnnouncements();

    boolean deleteAnnouncementById(int id);
}
