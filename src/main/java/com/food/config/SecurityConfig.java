package com.food.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService userDetailsService;

    // Bean for password encoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean for InternalResourceViewResolver (for JSP view resolution)
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    // Bean for AuthenticationManager
    

    // Main security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Disable CSRF if using stateless authentication; enable for session-based apps
            .authorizeHttpRequests(auth -> auth
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR).permitAll()  // Permit dispatcher types
                .requestMatchers("/users/register", "/users/login", "/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
    // Allow these URLs without authentication
                .anyRequest().authenticated()  // Require authentication for all other requests
            )
            .formLogin(form -> form
                .loginPage("/users/login")  // Custom login page
                .loginProcessingUrl("/users/login")  // Login processing URL
                .usernameParameter("email")  // Username field for login (email)
                .passwordParameter("password")  // Password field for login
                .defaultSuccessUrl("/recipe/form", true)  // Redirect to this page after successful login
                .failureUrl("/users/login?error=true")  // Redirect to login page with error parameter if login fails
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")  // Logout URL
                .logoutSuccessUrl("/users/login?logout=true")  // Redirect to login page after successful logout
                .permitAll()  // Allow everyone to access logout functionality
            )
            .sessionManagement(session -> session
                .sessionFixation().newSession()  // Protect against session fixation
                .invalidSessionUrl("/users/login?expired=true")  // Redirect to login page if session is invalid
            )
            .headers(headers -> headers
                .frameOptions().disable()  // Disable frame options for H2 console
            );

        return http.build();
    }
}
