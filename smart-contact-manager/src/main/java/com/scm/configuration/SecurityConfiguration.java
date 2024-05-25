package com.scm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    public SecurityConfiguration() {
        super();
        System.out.println("SecurityConfiguration");
    }

    // custom user details service
    @Autowired
    private SecurityCustomUserDetailsService customUserDetailsService;

    @Autowired
    private OAuthAuthenticationSuccessHandler authAuthenticationSuccessHandler;

    // create user and login using in-memory service
    /*
     * @Bean
     * public UserDetailsService userDetailsService() {
     * 
     * UserDetails user = User.withUsername("admin")
     * .password("admin")
     * .roles("ADMIN", "USER")
     * .build();
     * 
     * UserDetails user2 = User.withUsername("user")
     * .password("user123")
     * // .roles("ADMIN","USER")
     * .build();
     * 
     * var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user, user2);
     * return inMemoryUserDetailsManager;
     * }
     */

    // password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // dao authentication provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // managing routes(handlers) or url's
        httpSecurity.authorizeHttpRequests(authorize -> {
            // authorize.requestMatchers("/","/register").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        // form login
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/signin");
            formLogin.defaultSuccessUrl("/user/dashboard");
            // formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

        });

        // form logout
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        // oauth2 configurations
        httpSecurity.oauth2Login(oauth2 -> {
            oauth2.loginPage("/login");
            oauth2.successHandler(authAuthenticationSuccessHandler);
        });

        return httpSecurity.build();
    }
}
