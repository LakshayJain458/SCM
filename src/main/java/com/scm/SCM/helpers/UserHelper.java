package com.scm.SCM.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Objects;

public class UserHelper {
    public static String getLoggedInUserEmail(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken) {
            String clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var Oauth = (DefaultOAuth2User) authentication.getPrincipal();
            String username = "";
            if (clientId.equalsIgnoreCase("google")) {
                username = Objects.requireNonNull(Oauth.getAttribute("email")).toString();
                System.out.println("google" + username);
            } else if (clientId.equalsIgnoreCase("github")) {
                username = Oauth.getAttribute("email") != null ? Oauth.getAttribute("email") : Oauth.getAttribute("login") + "@gmail.com";
                System.out.println("github" + username);
            }
            return username;

        } else {
            System.out.println("database" + authentication.getName());
            return authentication.getName();
        }
    }

    public static String getLinkForEmailVerification(String emailToken) {
        return "http://localhost:8080/auth/verify-email?token=" + emailToken;
    }
}
