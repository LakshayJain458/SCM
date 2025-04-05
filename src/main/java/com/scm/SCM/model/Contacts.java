package com.scm.SCM.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Contacts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;
    private String ContactAddress;
    private String picture;
    private String ImagePublicId;
    private boolean favorite = false;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialHandles> links = new ArrayList<>();
    public void setLinks(List<SocialHandles> links) {
        this.links.clear();
        this.links.addAll(links);
        links.forEach(link -> link.setContact(this));
    }

}
