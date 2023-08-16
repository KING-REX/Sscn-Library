package com.sscn.library.security.config;

import com.sscn.library.security.filter.JwtAuthenticationFilter;
import com.sscn.library.service.UserPasswordService;
import com.sscn.library.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig
{
    private final UserService userService;
    private final UserPasswordService userPasswordService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final DaoAuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws InternalAuthenticationServiceException, IllegalArgumentException, Exception
    {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login", "/api/v1/auth/**", "/api/v1/authors").permitAll();
//                    auth.requestMatchers("/api/**").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint((request, response, authException) -> {
//                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().append("{" +
                                "\"error\":\"Login failed\"" +
                                ",\"errorType\":\"" + authException.getClass() + "\"" +
                                ",\"statusCode\":\"" + response.getStatus() + "\"" +
                                ",\"errorMessage\":\"" + authException.getMessage() + "\"" +
                                "}");
                    });
                })
                ;
//                .formLogin(form -> {
//                    form.loginPage("/login").permitAll();
////                    form.defaultSuccessUrl("/index", true); //Frontend url replaces the defaultSuccessUrl "/"
//                    form.passwordParameter("password");
//                    form.usernameParameter("username");
//                    form.failureHandler((request, response, exception) -> {
//                        response.getWriter().append("Access Denied.");
//                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    });
//                    form.successHandler((request, response, authentication) -> {
//                        response.getWriter().append("Access granted.");
//                        response.setStatus(HttpServletResponse.SC_OK);
//                    });
//                })
//                .logout(logout -> {
//                    logout
//                            .logoutUrl("/logout")
//                            .clearAuthentication(true)
//                            .invalidateHttpSession(true)
//                            .deleteCookies("JSESSIONID", "remember-me")
//                            .logoutSuccessUrl("/login");
//                });

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
