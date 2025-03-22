package com.scm.SCM.Controller;

import com.scm.SCM.Service.UserService;
import com.scm.SCM.forms.UserForm;
import com.scm.SCM.helpers.Message;
import com.scm.SCM.helpers.MessageType;
import com.scm.SCM.model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        return "home";
    }

    @RequestMapping("/home")
    public String home() {
        System.out.println("Home Page has been called");
        return "home";
    }

    @RequestMapping("/about")
    public String about() {
        System.out.println("About Page has been called");
        return "about";
    }

    @RequestMapping("/services")
    public String service() {
        System.out.println("Service Page has been called");
        return "services";
    }

    @GetMapping("/login")
    public String login(Model model) {
//        model.addAttribute("userForm", new UserForm());
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model,HttpSession session) {
        model.addAttribute("userForm", new UserForm());
        return "signup";
    }

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }

    @PostMapping(value = "/register")
    public String register(@Valid @ModelAttribute UserForm userform, BindingResult bindingResult, HttpSession session) {
        System.out.println("Registration processing.");
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        User user = new User();
        user.setUsername(userform.getUsername());
        user.setPassword(userform.getPassword());
        user.setEmail(userform.getEmail());
        user.setAbout(userform.getAbout());
        user.setPhoneNumber(userform.getPhoneNumber());

        try {
            userService.save(user);
            System.out.println("User saved successfully!");
            Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
            session.setAttribute("message", message);
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
            Message message = Message.builder().content("Registration Unsuccessful , Try Again !").type(MessageType.red).build();
            session.setAttribute("message", message);
        }
        return "redirect:/signup";
    }

}
