package com.project.security;

import com.project.security.jwt.JwtAuthenticationFilter;
import com.project.security.jwt.JwtAuthorizationFilter;
import com.project.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider tokenProvider;

  @Autowired
  public SecurityConfiguration(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.tokenProvider = tokenProvider;
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable().cors()
          .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .anyRequest().permitAll()
          .and()
        .addFilter(new JwtAuthenticationFilter(authenticationManagerBean(), tokenProvider))
        .addFilterBefore(new JwtAuthorizationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
