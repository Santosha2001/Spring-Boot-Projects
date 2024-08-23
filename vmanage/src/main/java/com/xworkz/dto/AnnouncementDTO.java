package com.xworkz.dto;

import lombok.Data;

import javax.persistence.Id;

@Data
public class AnnouncementDTO {

    @Id
    private int id;
    private String title;
    private String message;
}
