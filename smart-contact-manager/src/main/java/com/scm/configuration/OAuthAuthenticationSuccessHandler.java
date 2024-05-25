package com.scm.configuration;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repositories.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        // identify the provider the and get the details.

        var oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            // google
            // google attributes
            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setImage(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("Login using google");
            user.setPassword("google");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            // github
            // github attributes
            String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                    : oauthUser.getAttribute("login").toString() + "@gmail.com";

            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerId = oauthUser.getName();

            user.setEmail(email);
            user.setImage(picture);
            user.setName(name);
            user.setProviderId(providerId);
            user.setProvider(Providers.GITHUB);
            user.setAbout("Login using github");
            user.setPassword("github");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("facebook")) {
            // // facebook
            // // facebook attributes

        } else {
            logger.info("OAuthAuthenticationSuccessHandler: Unknown provider");
        }

        // get user details
        // -------------------------------------
        // DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

        // logger.info(user.getName());

        // user.getAttributes().forEach((key, value) -> {
        // logger.info("{}=>{}", key, value);
        // });

        // logger.info(user.getAuthorities().toString());

        // // save data to the database.
        // String email = user.getAttribute("email").toString();
        // String name = user.getAttribute("name").toString();
        // String imageUrl = user.getAttribute("picture").toString();

        // // create user and save to the database.
        // User userSave = new User();
        // userSave.setEmail(email);
        // userSave.setName(name);
        // userSave.setImage(imageUrl);
        // userSave.setEnabled(true);
        // userSave.setPassword("password");
        // userSave.setId(UUID.randomUUID().toString());
        // userSave.setProvider(Providers.GOOGLE);
        // userSave.setProviderId(user.getName());
        // userSave.setEmailVerified(true);
        // userSave.setRoleList(List.of(AppConstants.ROLE_USER));
        // userSave.setAbout("Account created usong Google.");

        // User user2 = userRepository.findByEmail(email).orElse(null);
        // if (user2 == null) {
        // userRepository.save(userSave);
        // logger.info("user saved: " + email);
        // }
        // -------------------------------------------

        // save user
        User user2 = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (user2 == null) {
            userRepository.save(user);
            logger.info("user saved: " + user.getEmail());
        }

        // DefaultRedirectStrategy is the implementation class of RedirectStrategy
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

    }

}
