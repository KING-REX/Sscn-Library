package com.sscn.library.security.service;

import com.sscn.library.entity.User;
import com.sscn.library.entity.UserRole;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.UserRepository;
import com.sscn.library.security.auth.AuthenticationRequest;
import com.sscn.library.security.auth.AuthenticationResponse;
import com.sscn.library.security.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("User %s not found".formatted(request.getUsername())));

        String token = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }
}
