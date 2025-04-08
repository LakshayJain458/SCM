package com.scm.SCM.Controller;

import com.scm.SCM.Service.ContactService;
import com.scm.SCM.Service.ImageService;
import com.scm.SCM.Service.UserService;
import com.scm.SCM.forms.ContactForm;
import com.scm.SCM.forms.searchContactForm;
import com.scm.SCM.helpers.AppConstants;
import com.scm.SCM.helpers.Message;
import com.scm.SCM.helpers.MessageType;
import com.scm.SCM.helpers.UserHelper;
import com.scm.SCM.model.Contacts;
import com.scm.SCM.model.SocialHandles;
import com.scm.SCM.model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

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
    public String viewContacts(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
                               @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                               @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
                               Authentication authentication, Model model) {
        String username = UserHelper.getLoggedInUserEmail(authentication);
        User user = userService.getUserByEmail(username);
        Page<Contacts> PageContacts = contactService.getByUser(user, page, size, sortBy, sortDirection);
        model.addAttribute("PageContacts", PageContacts);
        model.addAttribute("searchContactForm", new searchContactForm());
        model.addAttribute("PageSize", AppConstants.PAGE_SIZE);
        return "user/contact";
    }

    @GetMapping("/search")
    public String searchContact(
            @ModelAttribute searchContactForm searchContactForm,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model, Authentication authentication) {
        var user = userService.getUserByEmail(UserHelper.getLoggedInUserEmail(authentication));
        Page<Contacts> pageContact = null;
        if (searchContactForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(searchContactForm.getValue(), page, size, sortBy, direction, user);
        } else if (searchContactForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(searchContactForm.getValue(), page, size, sortBy, direction, user);
        } else if (searchContactForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhone(searchContactForm.getValue(), page, size, sortBy, direction, user);
        }
        model.addAttribute("searchContactForm", searchContactForm);
        model.addAttribute("PageContacts", pageContact);
        model.addAttribute("PageSize", AppConstants.PAGE_SIZE);
        return "user/search";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteContact(
            @PathVariable String id
    ) {
        try {
            contactService.deleteContactById(id);
            return ResponseEntity.ok().body(
                    Collections.singletonMap("message", "Contact deleted successfully")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Collections.singletonMap("message", "Error: " + e.getMessage())
            );
        }
    }

    @GetMapping("/edit/{id}")
    public String editContactForm(Model model, @PathVariable String id) {
        var contact = contactService.getContactById(id);
        ContactForm contactForm = new ContactForm();
        contactForm.setContactName(contact.getContactName());
        contactForm.setContactEmail(contact.getContactEmail());
        contactForm.setContactPhone(contact.getContactPhone());
        contactForm.setContactAddress(contact.getContactAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setContactPhoto(contact.getPicture());
        if (contact.getLinks() != null && !contact.getLinks().isEmpty()) {
            contactForm.setLinks(new ArrayList<>());
            for (SocialHandles handle : contact.getLinks()) {
                SocialHandles formHandle = new SocialHandles();
                formHandle.setHandle(handle.getHandle());
                formHandle.setHandleLink(handle.getHandleLink());
                contactForm.getLinks().add(formHandle);
            }
        }
        model.addAttribute("contactForm", contactForm);

        return "user/editContactForm";
    }

    @PostMapping("/update/{id}")
    public String updateContact(@PathVariable String id,@Valid @ModelAttribute ContactForm contactForm, HttpSession session , BindingResult bindingResult) {
       if (bindingResult.hasErrors()) {
           return "user/editContactForm";
       }
        var contact = contactService.getContactById(id);
        contact.setId(id);
        contact.setContactName(contactForm.getContactName());
        contact.setContactEmail(contactForm.getContactEmail());
        contact.setContactPhone(contactForm.getContactPhone());
        contact.setContactAddress(contactForm.getContactAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        if (contactForm.getLinks() != null && !contactForm.getLinks().isEmpty()) {
            for (SocialHandles handle : contactForm.getLinks()) {
                handle.setContact(contact);
            }
            contact.getLinks().addAll(contactForm.getLinks());
        }
        if (contactForm.getPicture() != null && !contactForm.getPicture().isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String fileUrl = imageService.uploadImage(contactForm.getPicture(), fileName);
            contact.setImagePublicId(fileName);
            contact.setPicture(fileUrl);
            contactForm.setContactPhoto(fileUrl);
        }
        contactService.update(contact);
        Message message = Message.builder().content("Updation Successful").type(MessageType.green).build();
        session.setAttribute("message", message);

        return "redirect:/user/contact/edit/"+id;
    }

}
