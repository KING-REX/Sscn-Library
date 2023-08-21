package com.sscn.library.service;

import com.sscn.library.entity.User;
import com.sscn.library.exception.DuplicateValueException;
import com.sscn.library.exception.NotFoundException;
import com.sscn.library.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService
{
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder)
  {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
  {
    return userRepository.findByUsername(username)
        .orElseThrow(() ->  new UsernameNotFoundException("User with username %s not found".formatted(username)));
  }

  public User addUser(User user)
  {
    if(userRepository.findByUsername(user.getUsername()).isPresent())
      throw new DuplicateValueException("User %s already exists".formatted(user.getUsername()));

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public void removeUser(String username)
  {
    removeUser(userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User %s doesn't exist.".formatted(username))));
  }

  public void removeUser(User user)
  {
    if(userRepository.findByUsername(user.getUsername()).isEmpty())
      throw new NotFoundException("User %s doesn't exist".formatted(user.getUsername()));

    userRepository.delete(user);
  }
}
