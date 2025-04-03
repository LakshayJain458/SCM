package com.scm.SCM.Service;

import com.scm.SCM.model.Contacts;
import com.scm.SCM.model.User;
import com.scm.SCM.repo.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

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

    public void getByUserId(String userId) {
        contactRepo.findByUserId(userId);
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

