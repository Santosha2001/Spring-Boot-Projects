package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserForm {

    @NotBlank(message = "*username is required.")
    @Size(min = 3, message = "*min 3 characters required.")
    private String name;

    @NotBlank(message = "*email is required.")
    @Email(message = "*invalid email.")
    private String email;

    @NotBlank(message = "*password is required.")
    @Size(min = 6, message = "*min 6 characters required.")
    private String password;

    @NotBlank(message = "*mobile is required.")
    @Size(min = 10, max = 10, message = "*min 10 characters required.")
    private String mobile;

    @NotBlank(message = "*about is required.")
    private String about;
}
