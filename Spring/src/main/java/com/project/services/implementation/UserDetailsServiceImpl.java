package com.project.services.implementation;

import com.project.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String usernameOrMobileNumber) throws UsernameNotFoundException {
    return userService.findByUsernameOrMobileNumber(usernameOrMobileNumber);
  }
}
