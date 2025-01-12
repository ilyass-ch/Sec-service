package com.example.demo.sec;


import com.example.demo.sec.filters.JwtAuthenticationFilter;
import com.example.demo.sec.filters.JwtAuthorizationFilter;
import com.example.demo.sec.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   final AuthenticationConfiguration authenticationConfiguration)
            throws Exception {

        http
                .headers(header -> header.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable
                ))
               .sessionManagement(
                        sss->sss.sessionCreationPolicy(
                               SessionCreationPolicy.STATELESS
                        )
                )
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        aut -> aut.requestMatchers("/h2-console/**","/refreshToken/**").permitAll()
                )
//                .authorizeHttpRequests(aut -> aut.requestMatchers(HttpMethod.GET,"/users/**").hasAuthority("USER"))
//                .authorizeHttpRequests(aut -> aut.requestMatchers(HttpMethod.POST,"/users/**").hasAuthority("ADMIN"))
       //         .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

        return http.build();
    }



}

