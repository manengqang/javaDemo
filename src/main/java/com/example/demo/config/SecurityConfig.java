package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // JWT 接口风格，关闭 CSRF，避免无 token 的 POST 直接 403
            .csrf(csrf -> csrf.disable());
            // .authorizeHttpRequests(auth -> auth
            //     .requestMatchers("/login2").permitAll()
            //     .requestMatchers("/register2").permitAll()
            //     .requestMatchers("/error").permitAll()
            //     .anyRequest().authenticated()
            // )
            // .formLogin(form -> form.disable())
            // .httpBasic(basic -> {})
            // .exceptionHandling(handling -> handling
            //     .authenticationEntryPoint((request, response, authException) -> {
            //         log.info("Unauthorized access attempt to: " + request.getRequestURI());
            //         response.sendError(401, "Unauthorized");
            //     }));
        
        log.info("Security configuration applied. Permitted paths: /login2, /register2");
        return http.build();
    }

    // @Bean
    // public WebSecurityCustomizer webSecurityCustomizer() {
    //     return (web) -> web.ignoring().requestMatchers("/register2");
    // }
}