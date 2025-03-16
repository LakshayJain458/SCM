package com.scm.SCM.Service;

import com.scm.SCM.model.User;
import com.scm.SCM.repo.userRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private userRepo userRepo;

    public void save(User user) {
        userRepo.save(user);
    }
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    public void deleteUserById(String id) {
        userRepo.deleteById(id);
    }
    public void updateUser(User user) {
        userRepo.save(user);
    }
}
