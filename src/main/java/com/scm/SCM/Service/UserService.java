package com.scm.SCM.Service;

import com.scm.SCM.helpers.AppConstants;
import com.scm.SCM.model.User;
import com.scm.SCM.repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private userRepo userRepo;

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        userRepo.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void deleteUserById(String id) {
        User dbUser = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        userRepo.delete(dbUser);
    }

    public boolean userExists(String id) {
        User dbuser = userRepo.findById(id).orElse(null);
        return dbuser != null;
    }

    public boolean userExistsByEmail(String email) {
        User dbuser = userRepo.findByEmail(email).orElse(null);
        return dbuser != null;
    }

    public User updateUser(User user) {
        User dbUser = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + user.getUserId()));

        if (user.getUsername() != null) dbUser.setUsername(user.getUsername());
        if (user.getPassword() != null) dbUser.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getEmail() != null) dbUser.setEmail(user.getEmail());
        if (user.getProfilePicture() != null) dbUser.setProfilePicture(user.getProfilePicture());
        if (user.getAbout() != null) dbUser.setAbout(user.getAbout());
        if (user.getPhoneNumber() != null) dbUser.setPhoneNumber(user.getPhoneNumber());
        if (user.getProvider() != null) dbUser.setProvider(user.getProvider());
        dbUser.setProviderId(user.getProviderId());
        dbUser.setEnabled(user.isEnabled());
        dbUser.setEmailVerified(user.isEmailVerified());
        dbUser.setPhoneNumberVerified(user.isPhoneNumberVerified());

        return userRepo.save(dbUser);
    }

    public User getUserByEmail(String email) {
         return userRepo.findByEmail(email).orElse(null);
    }


}
