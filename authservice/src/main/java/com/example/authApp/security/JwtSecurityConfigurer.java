package com.example.authApp.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.authApp.security.JwtAuthEntryPoint;
import com.example.authApp.security.JwtTokenAuthFilter;
import com.example.authApp.security.JwtTokenProvider;

public class JwtSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenProvider jwtTokenProvider;

    public JwtSecurityConfigurer(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtTokenAuthFilter customFilter = new JwtTokenAuthFilter(jwtTokenProvider);
        http.exceptionHandling()
        .authenticationEntryPoint(new JwtAuthEntryPoint())
        .and()
        .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
