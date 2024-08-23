package com.xworkz.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "announcement_table")
@NamedQuery(name = "getAllAnnouncement", query = "SELECT entity from  AnnouncementEntity entity")
public class AnnouncementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String message;
}
