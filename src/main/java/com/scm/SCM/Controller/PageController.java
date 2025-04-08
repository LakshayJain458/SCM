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
        return "home";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/services")
    public String service() {
        return "services";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/clear-session-message")
    @ResponseBody
    public void clearSessionMessage(HttpSession session) {
        session.removeAttribute("message");
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "signup";
    }

    @GetMapping("/contactMe")
    public String contact() {
        return "ContactMe";
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
        user.setEnabled(false);

        try {
            userService.save(user);
            Message message = Message.builder().content("Registration Successful , Now login!").type(MessageType.green).build();
            session.setAttribute("message", message);
        } catch (Exception e) {
            Message message = Message.builder().content("Registration Unsuccessful , Try Again !").type(MessageType.red).build();
            session.setAttribute("message", message);
        }
        return "redirect:/signup";
    }

}
