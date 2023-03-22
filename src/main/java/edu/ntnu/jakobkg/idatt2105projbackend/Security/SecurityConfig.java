package edu.ntnu.jakobkg.idatt2105projbackend.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import edu.ntnu.jakobkg.idatt2105projbackend.service.UserService;

@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests()
                    // The login endpoint is open to anyone
                    .requestMatchers("/login").permitAll()

                    // USER ENDPOINTS

                    // The endpoints to create and fetch a user are open to anyone
                    .requestMatchers(HttpMethod.GET, "/user").permitAll()
                    .requestMatchers(HttpMethod.POST, "/user").permitAll()

                    // The endpoints to change or delete a user require the user to be logged in
                    .requestMatchers(HttpMethod.DELETE, "/user").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/user").authenticated()

                    // Only admins can access the endpoint that changes whether a user is an admin
                    .requestMatchers(HttpMethod.POST, "/user/admin*").hasAuthority("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.POST, "/category").hasAuthority("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/category/*").hasAuthority("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/category/*").hasAuthority("ROLE_ADMIN")
                    .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(new AuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
