package com.project.services;

import com.project.model.credential.Authority;
import com.project.model.credential.RegistrationForm;
import com.project.model.credential.Role;
import com.project.model.credential.User;
import com.project.model.exception.DataNotFoundException;
import com.project.repository.credential.RoleRepository;
import com.project.repository.credential.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User findByUsernameOrMobileNumber(String usernameOrMobileNumber){
    return userRepository
        .findByUsername(usernameOrMobileNumber)
        .orElseGet(()->
            userRepository
                .findByMobileNumber(usernameOrMobileNumber)
                .orElseThrow(()->new UsernameNotFoundException("User doesn't exist!"))
        );
  }

  public User regNewUserFromRegistrationForm(RegistrationForm form){
    User user = new User();
    user.setIsNonExpired(true);
    user.setUsername(form.getUsername());
    user.setPassword(passwordEncoder.encode(form.getPassword()));
    user.setRole(
        roleRepository
            .findByAuthority(Authority.USER)
            .orElseThrow(()->new DataNotFoundException("Role with current Authority doesn't exist!"))
    );
    return userRepository.save(user);
  }
}
