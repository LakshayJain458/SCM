package com.scm.SCM.Controller;

import com.scm.SCM.Service.ContactService;
import com.scm.SCM.Service.ImageService;
import com.scm.SCM.Service.UserService;
import com.scm.SCM.forms.ContactForm;
import com.scm.SCM.helpers.Message;
import com.scm.SCM.helpers.MessageType;
import com.scm.SCM.helpers.UserHelper;
import com.scm.SCM.model.Contacts;
import com.scm.SCM.model.SocialHandles;
import com.scm.SCM.model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.boot.model.source.internal.hbm.Helper;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.UUID;

@Controller
@RequestMapping("/user/contact")
public class ContactsController {

    Logger logger = LoggerFactory.getLogger(ContactsController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/addContact")
    public String addContacts(Model model) {
        ContactForm contactForm = new ContactForm();
        contactForm.setLinks(new ArrayList<>());
        model.addAttribute("contactForm", contactForm);
        return "user/addContact";
    }

    @PostMapping("/saveContact")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult, HttpSession session, Authentication authentication) {
        System.out.println(contactForm);

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> logger.error(error.getDefaultMessage()));
            Message message = Message.builder().content("Contact Add Unsuccessful , Try Again !").type(MessageType.red).build();
            session.setAttribute("message", message);
            return "user/addContact";
        }

        String username = UserHelper.getLoggedInUserEmail(authentication);
        User user = userService.getUserByEmail(username);
        logger.info(contactForm.getPicture().getOriginalFilename());

        Contacts contacts = new Contacts();
        contacts.setContactName(contactForm.getContactName());
        contacts.setContactEmail(contactForm.getContactEmail());
        contacts.setContactPhone(contactForm.getContactPhone());
        contacts.setContactAddress(contactForm.getContactAddress());
        contacts.setDescription(contactForm.getDescription());
        contacts.setFavorite(contactForm.isFavorite());
        contacts.setUser(user);

        if (contactForm.getLinks() != null && !contactForm.getLinks().isEmpty()) {
            for (SocialHandles handle : contactForm.getLinks()) {
                handle.setContact(contacts);
            }
            contacts.getLinks().addAll(contactForm.getLinks());
        }
        if (contactForm.getPicture() != null && !contactForm.getPicture().isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String fileUrl = imageService.uploadImage(contactForm.getPicture(), fileName);
            contacts.setImagePublicId(fileName);
            contacts.setPicture(fileUrl);
        }

        contactService.save(contacts);
        Message message = Message.builder().content("Contact Added Successfully").type(MessageType.green).build();
        session.setAttribute("message", message);
        return "redirect:/user/contact/addContact";
    }

    @GetMapping("/viewContact")
    public String viewContacts(Model model) {
        return "user/contact";
    }

}
