package com.project.services;

import com.project.model.credential.Authority;
import com.project.model.credential.RegistrationForm;
import com.project.model.credential.Role;
import com.project.model.credential.User;
import com.project.model.exception.DataNotFoundException;
import com.project.repository.credential.RoleRepository;
import com.project.repository.credential.UserRepository;
import com.project.services.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("<= UserService Test =>")
public class UserServiceTest {

  private long counter;

  private UserService userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    counter = 0;
    passwordEncoder = new BCryptPasswordEncoder();
    userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
  }

  @Test
  @DisplayName("<= find user by username =>")
  void findUserByUsername() {
    User user = initUser();

    when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
    assertThat(userService.findByUsernameOrMobileNumber(user.getUsername())).isEqualTo(user);
  }

  @Test
  @DisplayName("<= find user by phone =>")
  void findUserByPhone() {
    User user = initUser();
    when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    when(userRepository.findByMobileNumber(any(String.class))).thenReturn(Optional.of(user));

    assertThat(userService.findByUsernameOrMobileNumber(user.getUsername())).isEqualTo(user);
  }

  @Test
  @DisplayName("<= find user by username or phone when doesn't exists =>")
  void findUserByUsernameOrMobileNumberWhenDoesntExist() {
    String usernameOrPhone = "usernameOrPhone";

    when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    when(userRepository.findByMobileNumber(any(String.class))).thenReturn(Optional.empty());

    assertAll(
        () -> assertThatThrownBy(() -> userService.findByUsernameOrMobileNumber(usernameOrPhone))
            .isInstanceOf(UsernameNotFoundException.class),
        () -> assertThatThrownBy(() -> userService.findByUsernameOrMobileNumber(usernameOrPhone))
            .hasMessageContaining(usernameOrPhone)
    );
  }

  @Test
  @DisplayName("<= reg new user from registration form =>")
  void regNewUserFromRegistrationForm() {
    RegistrationForm form = new RegistrationForm("Username", "Password");


    Role role = new Role();
    User toCheck = initUser(form.getPassword());
    toCheck.setRole(role);

    when(roleRepository.findByAuthority(any(Authority.class))).thenReturn(Optional.of(role));
    when(userRepository.save(any(User.class))).thenReturn(toCheck);

    assertThat(userService.regNewUserFromRegistrationForm(form)).isEqualTo(toCheck);
  }

  @Test
  @DisplayName("<= reg new user when role doesn't exists =>")
  void regNewUserWhenRoleDoesntExist() {
    when(roleRepository.findByAuthority(any(Authority.class))).thenReturn(Optional.empty());

    assertThatThrownBy(
        () -> userService.regNewUserFromRegistrationForm(new RegistrationForm("", ""))
    ).isInstanceOf(DataNotFoundException.class);
  }

  @Test
  @DisplayName("<= find all roles =>")
  void findAllRoles() {
    List<Role> roles = Arrays.stream(Authority.values()).map(a -> new Role(counter++, a)).collect(Collectors.toList());

    when(roleRepository.findAll()).thenReturn(roles);
    assertThat(userService.findAllRoles()).isEqualTo(roles);
  }

  @Test
  @DisplayName("<= find user by id =>")
  void findUserById() {
    User user = initUser();

    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
    assertThat(userService.findUserById(user.getId())).isEqualTo(user);
  }

  @Test
  @DisplayName("<= find user by id when doesn't exists =>")
  void findUserByIdWhenDoesntExists() {
    Long userId = 1L;
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
    assertThatThrownBy(
        () -> userService.findUserById(userId)
    ).isInstanceOf(DataNotFoundException.class);
  }

  @Test
  @DisplayName("<= find all Users =>")
  void findAllUsers() {
    List<User> users = IntStream
        .range(0, 15)
        .mapToObj(n -> initUser())
        .collect(Collectors.toList());

    when(this.userRepository.findAll()).thenReturn(users);

    assertThat(this.userService.findAll()).isEqualTo(users);
  }

  @Test
  @DisplayName("<= delete User by id =>")
  void deleteUserById() {
    User user = initUser();
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
    assertAll(
        ()->assertDoesNotThrow(() -> userService.deleteUserById(user.getId()))
    );
  }

  @Test
  @DisplayName("<= update User =>")
  void updateUser(){
    User user = initUser();
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenReturn(user);
    assertAll(
        ()->assertDoesNotThrow(() -> userService.update(user)),
        ()->assertThat(userService.update(user)).isEqualTo(user)
    );
  }


  private User initUser() {
    return initUser("" + counter);
  }

  private User initUser(String password) {
    User user = new User();
    user.setId(counter++);
    user.setRole(new Role(1L, Authority.USER));
    user.setPassword(passwordEncoder.encode(password));
    user.setIsNonExpired(true);
    user.setUsername("" + counter);
    return user;
  }
}
