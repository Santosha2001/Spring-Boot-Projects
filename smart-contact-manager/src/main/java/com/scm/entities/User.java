package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
// @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    private String password;

    @Column(length = 5000)
    private String about;

    @Column(length = 5000)
    private String image;
    private String mobile;

    private boolean enabled = false;
    private boolean emailVerified = false;
    private boolean mobileVerified = false;

    // SELF, GOOGLE, FACEBOOK, GITHUB
    @Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.SELF;
    private int providerId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> Contacts = new ArrayList<>();

}
