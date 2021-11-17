package com.project.services;

import com.project.model.credential.User;
import com.project.repository.credential.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findByUsername(String username){
    return userRepository
        .findByUsername(username)
        .orElseThrow(()->new UsernameNotFoundException("User doesn't exist"));
  }
}
