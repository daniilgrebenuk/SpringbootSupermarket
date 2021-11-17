package com.project.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserService userService;

  public UserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userService.findByUsername(username);
  }
}
