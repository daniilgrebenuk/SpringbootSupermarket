package com.project.services;

import com.project.model.credential.Authority;
import com.project.model.credential.Role;
import com.project.model.credential.User;
import com.project.services.implementation.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("<= UserDetailsService Test =>")
public class UserDetailsServiceTest {

  private UserDetailsService userDetailsService;
  private long counter;

  @Mock
  private UserService userService;

  @BeforeEach
  void setUp() {
    userDetailsService = new UserDetailsServiceImpl(userService);
    counter = 0;
  }

  @Test
  @DisplayName("<= get UserDetails if exists =>")
  void loadUserByUsername() {
    User user = initUser();

    when(userService.findByUsernameOrMobileNumber(any(String.class))).thenReturn(user);

    assertThat(userDetailsService.loadUserByUsername(user.getUsername())).isEqualTo(user);
  }

  @Test
  @DisplayName("<= get UserDetails if User doesn't exist =>")
  void loadUserByUsernameWhenDoesntExists() {
    String username = "username";

    when(userService.findByUsernameOrMobileNumber(any(String.class))).thenThrow(new UsernameNotFoundException(""));


    assertThatThrownBy(()->userDetailsService.loadUserByUsername(username))
        .isInstanceOf(UsernameNotFoundException.class);
  }

  private User initUser() {
    User user = new User();
    user.setId(counter++);
    user.setRole(new Role(1L, Authority.USER));
    user.setPassword("" + counter);
    user.setIsNonExpired(true);
    user.setUsername("" + counter);
    return user;
  }
}
