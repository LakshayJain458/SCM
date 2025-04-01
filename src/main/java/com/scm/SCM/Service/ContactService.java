package com.scm.SCM.Service;

import com.scm.SCM.forms.ContactForm;
import com.scm.SCM.model.Contacts;
import com.scm.SCM.model.SocialHandles;
import com.scm.SCM.repo.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
        contactRepo.save(contacts);
    }

    public List<Contacts> getAllContacts() {
        return contactRepo.findAll();
    }
    public Optional<Contacts> getContactById(String id) {
        return contactRepo.findById(id);
    }
    public void deleteContactById(String id) {
        contactRepo.deleteById(id);
    }

    public List<Contacts> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

}

