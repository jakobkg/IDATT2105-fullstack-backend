package edu.ntnu.jakobkg.idatt2105projbackend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
                    .requestMatchers("/login").permitAll()
                    .requestMatchers(HttpMethod.GET, "/user").permitAll()
                    .requestMatchers(HttpMethod.POST, "/user").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/user").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/user").authenticated()
                    .requestMatchers(HttpMethod.POST, "/user/admin*").hasAuthority("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.POST, "/item").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/item/*").authenticated()
                    .requestMatchers(HttpMethod.GET, "/item").permitAll()
                    .requestMatchers(HttpMethod.GET, "/item/*").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/item/*").authenticated()
                    .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(new AuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
