package com.scm.SCM.Configurations;

import com.scm.SCM.helpers.AppConstants;
import com.scm.SCM.model.Providers;
import com.scm.SCM.model.User;
import com.scm.SCM.repo.userRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private userRepo userRepo;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var Oauth = (DefaultOAuth2User) authentication.getPrincipal();
        Oauth.getAttributes();

        Oauth.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });

        User newUser = new User();
        newUser.setPassword(null);
        newUser.setEnabled(true);
        newUser.setRoleList(List.of(AppConstants.ROLE_USER));
        newUser.setEmailVerified(true);
        newUser.setEmail(Oauth.getAttribute("email"));
        newUser.setProfilePicture(Oauth.getAttribute("profilePicture"));
        newUser.setUsername(Oauth.getAttribute("name"));
        newUser.setProviderId(Oauth.getName());
        newUser.setAbout("This account is from Google");
        newUser.setProvider(Providers.GOOGLE);

        User currUser = userRepo.findByEmail(newUser.getEmail()).orElse(null);
        if (currUser == null) {
            userRepo.save(newUser);
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
