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
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private userRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String Registrationid = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        var Oauth = (DefaultOAuth2User) authentication.getPrincipal();

        User user = new User();
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        if (Registrationid.equalsIgnoreCase("google")) {
            user.setEmail(Objects.requireNonNull(Oauth.getAttribute("email")).toString());
            user.setProfilePicture(Objects.requireNonNull(Oauth.getAttribute("picture")).toString());
            user.setUsername(Objects.requireNonNull(Oauth.getAttribute("name")).toString());
            user.setProvider(Providers.GOOGLE);
            user.setProviderId(Oauth.getName());
            user.setAbout("This account is from Google");

        } else if (Registrationid.equalsIgnoreCase("github")) {
            String email = Oauth.getAttribute("email") != null ? Oauth.getAttribute("email") : Oauth.getAttribute("login") + "@gmail.com";
            user.setEmail(email);
            user.setProfilePicture(Objects.requireNonNull(Oauth.getAttribute("avatar_url")).toString());
            user.setUsername(Objects.requireNonNull(Oauth.getAttribute("login")).toString());
            user.setProvider(Providers.GITHUB);
            user.setProviderId(Oauth.getName());
            user.setAbout("This account is from Github");

        }

        User currUser = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (currUser == null) {
            userRepo.save(user);
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
