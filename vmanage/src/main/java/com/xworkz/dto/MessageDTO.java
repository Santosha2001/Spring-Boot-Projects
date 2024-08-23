package com.xworkz.dto;

import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class MessageDTO {

    @Id
    private int id;
    private String content;
    private int senderId;
    private LocalDateTime timestamp;
}
