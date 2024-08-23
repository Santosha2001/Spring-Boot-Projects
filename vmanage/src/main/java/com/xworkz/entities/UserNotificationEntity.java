package com.xworkz.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "usernotification")
@NamedQuery(name = "getUserNotificationByUserId", query = "SELECT entity FROM UserNotificationEntity entity WHERE entity.userId=:userId")
@NamedQuery(name = "getallnotification", query = "SELECT entity FROM UserNotificationEntity entity")
public class UserNotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id")
    private MessageEntity message;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "is_read", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isRead;

}
