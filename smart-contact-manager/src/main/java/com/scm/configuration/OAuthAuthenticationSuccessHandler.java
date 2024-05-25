package com.scm.configuration;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

        // get user details
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

        logger.info(user.getName());

        user.getAttributes().forEach((key, value) -> {
            logger.info("{}=>{}", key, value);
        });

        logger.info(user.getAuthorities().toString());

        // save data to the database.
        String email = user.getAttribute("email").toString();
        String name = user.getAttribute("name").toString();
        String imageUrl = user.getAttribute("picture").toString();

        // create user and save to the database.
        User userSave = new User();
        userSave.setEmail(email);
        userSave.setName(name);
        userSave.setImage(imageUrl);
        userSave.setEnabled(true);
        userSave.setPassword("password");
        userSave.setId(UUID.randomUUID().toString());
        userSave.setProvider(Providers.GOOGLE);
        userSave.setProviderId(user.getName());
        userSave.setEmailVerified(true);
        userSave.setRoleList(List.of(AppConstants.ROLE_USER));
        userSave.setAbout("Account created usong Google.");

        User user2 = userRepository.findByEmail(email).orElse(null);
        if (user2 == null) {
            userRepository.save(userSave);
            logger.info("user saved: " + email);
        }

        // DefaultRedirectStrategy is the implementation class of RedirectStrategy
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

    }

}
