package com.scm.SCM.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SocialHandles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SocialHandles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String handle;
    private String handleLink;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private Contacts contact;
}
