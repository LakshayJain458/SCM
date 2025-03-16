// npx tailwindcss -i .\src\main\resources\static\css\input.css -o .\src\main\resources\static\css\output.css --watch

package com.scm.SCM.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private String userId;

    @Column(nullable = false)
    private String username;

    private String password;

    @Column(unique = true , nullable = false)
    private String email;

    private boolean emailVerified = false;
    private String phoneNumber;
    private boolean phoneNumberVerified = false;

    @Column(length = 1000)
    private String profilePicture;

    @Column(length = 1000)
    private String about;

    private boolean enabled = false;
    private Providers provider = Providers.SELF;
    private String providerId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contacts> contacts = new ArrayList<>();
}
