package com.sscn.library.service;

import com.sscn.library.entity.User;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordService implements UserDetailsPasswordService {

    private UserRepository userRepository;

    public UserPasswordService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        User existingUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() ->  new NotFoundException("User with username %s not found".formatted(user.getUsername())));
        existingUser.setPassword(newPassword);
        return userRepository.save(existingUser);
    }
}
