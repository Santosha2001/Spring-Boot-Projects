package com.vmanager.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VendorForm {

    @NotBlank(message = "*username is required.")
    @Size(min = 3, message = "*min 3 characters required.")
    private String name;

    @NotBlank(message = "*email is required.")
    @Email(message = "*invalid email.")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "*invalid format")
    private String email;

    @NotBlank(message = "*password is required.")
    @Size(min = 6, message = "*min 6 characters required.")
    private String password;

    @NotBlank(message = "*mobile is required.")
    @Size(min = 10, max = 10, message = "*min 10 characters required.")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "*invalid phone number")
    private String phone;

    @NotBlank(message = "*address is required.")
    private String address;

    @Pattern(regexp = "^\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}Z[A-Z\\d]{1}$", message = "*invalid gst number")
    @NotBlank(message = "*gst required.")
    private String gst;
}
