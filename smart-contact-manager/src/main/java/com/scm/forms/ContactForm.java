package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ContactForm {

    private String name;
    private String email;
    private String mobile;
    private String address;
    private MultipartFile image;
    private String description;
    private boolean favourite;
    private String websiteLink;
    private String linkedInLink;

}
