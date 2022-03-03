package com.project.services.implementation;

import com.project.model.credential.Authority;
import com.project.model.credential.RegistrationForm;
import com.project.model.credential.Role;
import com.project.model.credential.User;
import com.project.model.exception.DataNotFoundException;
import com.project.repository.credential.RoleRepository;
import com.project.repository.credential.UserRepository;
import com.project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;


  @Override
  public User findByUsernameOrMobileNumber(String usernameOrMobileNumber) {
    return userRepository
        .findByUsername(usernameOrMobileNumber)
        .orElseGet(() ->
            userRepository
                .findByMobileNumber(usernameOrMobileNumber)
                .orElseThrow(() -> new UsernameNotFoundException(
                    "User with username or mobile number :\"" + usernameOrMobileNumber + "\" doesn't exist!")
                )
        );
  }

  @Override
  public User regNewUserFromRegistrationForm(RegistrationForm form) {
    User user = new User();
    user.setIsNonExpired(true);
    user.setUsername(form.getUsername());
    user.setPassword(passwordEncoder.encode(form.getPassword()));
    user.setRole(
        roleRepository
            .findByAuthority(Authority.USER)
            .orElseThrow(
                () -> new DataNotFoundException("Role with authority:{" + Authority.USER.name() + "} doesn't exist!")
            )
    );
    return save(user);
  }

  @Override
  public List<Role> findAllRoles() {
    return roleRepository.findAll();
  }

  @Override
  public User findUserById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User with id: " + id + " doesn't exist!"));
  }

  @Override
  public User save(User user) {
    return this.userRepository.save(user);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public void deleteUserById(Long id) {

    this.userRepository.delete(findUserById(id));
  }

  @Override
  public User update(User user) {
    findUserById(user.getId());
    return userRepository.save(user);
  }
}
