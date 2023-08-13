package com.sscn.library.security;

import com.sscn.library.service.UserService;
import static com.sscn.library.entity.UserRole.ADMIN;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder)
    {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/", "/index.html").permitAll();
//                    auth.requestMatchers("/api/**").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> {
                    form.loginPage("/login").permitAll();
                    form.defaultSuccessUrl("/", true); //Frontend url replaces the defaultSuccessUrl "/"
                    form.passwordParameter("password");
                    form.usernameParameter("username");
                })
                .logout(logout -> {
                    logout
                            .logoutUrl("/logout")
                            .clearAuthentication(true)
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID", "remember-me")
                            .logoutSuccessUrl("/login");
                });
//                .httpBasic(httpBasic -> {});

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}
