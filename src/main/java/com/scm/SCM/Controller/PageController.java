package com.scm.SCM.Controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

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
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }

    @PostMapping(value = "/register")
    public String register(){
        System.out.println("Registration processing.");
        return "redirect:/home";
    }

}
