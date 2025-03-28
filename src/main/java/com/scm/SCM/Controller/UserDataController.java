package com.scm.SCM.Controller;

import com.scm.SCM.Service.UserService;
import com.scm.SCM.helpers.UserHelper;
import com.scm.SCM.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserDataController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUser(Authentication authentication , Model model){
        if (authentication==null){
            return;
        }
        String username = UserHelper.getLoggedInUserEmail(authentication);
        User user = userService.getUserByEmail(username);
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        model.addAttribute("LoggedInUser", user);
    }
}
