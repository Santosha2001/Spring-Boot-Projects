package com.xworkz.services.impl;

import com.xworkz.dto.AnnouncementDTO;
import com.xworkz.entities.AnnouncementEntity;
import com.xworkz.repositories.AnnouncementRepository;
import com.xworkz.services.AnnouncementService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Override
    public boolean saveAnnouncement(AnnouncementDTO announcementDTO) {
        if (announcementDTO != null) {
            AnnouncementEntity announcementEntity = new AnnouncementEntity();
            BeanUtils.copyProperties(announcementDTO, announcementEntity);
            boolean save = this.announcementRepository.saveAnnouncement(announcementEntity);
            return save;
        }
        return false;
    }

    @Override
    public List<AnnouncementDTO> getAllAnnouncements() {
        List<AnnouncementEntity> entities = this.announcementRepository.getAllAnnouncements();
        if (entities != null) {
            return entities.stream().map(entity -> {
                AnnouncementDTO dto = new AnnouncementDTO();
                BeanUtils.copyProperties(entity, dto);
                return dto;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public boolean deleteAnnouncementById(int id) {
        return this.announcementRepository.deleteAnnouncementById(id);
    }
}
