package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ContactForm {

    @NotBlank(message = "*username is required.")
    @Size(min = 3, message = "*min 3 characters required.")
    private String name;

    @NotBlank(message = "*email is required.")
    @Email(message = "*invalid email.")
    private String email;

    @NotBlank(message = "*mobile is required.")
    @Size(min = 10, max = 10, message = "*min 10 characters required.")
    private String mobile;

    @NotBlank(message = "*address is required.")
    private String address;

    private MultipartFile contactImage;
    private String description;
    private boolean favourite;
    private String websiteLink;
    private String linkedInLink;

}
