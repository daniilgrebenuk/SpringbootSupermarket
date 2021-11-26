package com.project.services;

import com.project.model.credential.User;
import com.project.repository.credential.RoleRepository;
import com.project.repository.credential.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Autowired
  public UserService(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  public User findByUsernameOrMobileNumber(String usernameOrMobileNumber){
    return userRepository
        .findByUsername(usernameOrMobileNumber)
        .orElse(
            userRepository
                .findByMobileNumber(usernameOrMobileNumber)
                .orElseThrow(()->new UsernameNotFoundException("User doesn't exist!"))
        );
  }
}
