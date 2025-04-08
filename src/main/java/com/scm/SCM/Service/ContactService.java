package com.scm.SCM.Service;

import com.scm.SCM.model.Contacts;
import com.scm.SCM.model.SocialHandles;
import com.scm.SCM.model.User;
import com.scm.SCM.repo.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepo contactRepo;

    public void save(Contacts contacts) {
        contactRepo.save(contacts);
    }

    public void update(Contacts contacts) {
        var oldContact = contactRepo.findById(contacts.getId()).orElseThrow();
        oldContact.setUser(contacts.getUser());
        oldContact.setContactName(contacts.getContactName());
        oldContact.setContactEmail(contacts.getContactEmail());
        oldContact.setContactPhone(contacts.getContactPhone());
        oldContact.setContactAddress(contacts.getContactAddress());
        oldContact.setFavorite(contacts.isFavorite());
        oldContact.setDescription(contacts.getDescription());
        oldContact.setPicture(contacts.getPicture());
        oldContact.setImagePublicId(contacts.getImagePublicId());
        List<SocialHandles> newLinks = contacts.getLinks();
        if (newLinks != null) {
            oldContact.getLinks().clear();
            for (SocialHandles handle : newLinks) {
                SocialHandles newHandle = new SocialHandles();
                newHandle.setHandle(handle.getHandle());
                newHandle.setHandleLink(handle.getHandleLink());
                newHandle.setContact(oldContact);
                oldContact.getLinks().add(newHandle);
            }
        } else {
            oldContact.getLinks().clear();
        }

        contactRepo.save(oldContact);
    }

    public List<Contacts> getAllContacts() {
        return contactRepo.findAll();
    }

    public Contacts getContactById(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    public void deleteContactById(String id) {
        contactRepo.deleteById(id);
    }

    public List<Contacts> getByUserId(String userId) {
        return  contactRepo.findByUserId(userId);
    }

    public Page<Contacts> getByUser(User user, int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUser(user, pageable);
    }

    public Page<Contacts> searchByName(String name, int page, int size, String sortBy, String sortDirection, User user) {
        Sort sort = sortDirection.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndContactNameContaining(user, name, pageable);
    }

    public Page<Contacts> searchByPhone(String phone, int page, int size, String sortBy, String sortDirection, User user) {
        Sort sort = sortDirection.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndContactPhoneContaining(user, phone, pageable);
    }

    public Page<Contacts> searchByEmail(String email, int page, int size, String sortBy, String sortDirection, User user) {
        Sort sort = sortDirection.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndContactEmailContaining(user, email, pageable);
    }
}

