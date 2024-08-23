package com.xworkz.services;

import com.xworkz.dto.AnnouncementDTO;

import java.util.List;

public interface AnnouncementService {

    boolean saveAnnouncement(AnnouncementDTO announcementDTO);

    List<AnnouncementDTO> getAllAnnouncements();

    boolean deleteAnnouncementById(int id);
}
