package com.project.security;

import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider tokenProvider;

  public JwtAuthorizationFilter(JwtTokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if (request.getServletPath().equals("/api/login")){
      filterChain.doFilter(request, response);
      return;
    }
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
      try{
        tokenProvider.verifyTokenAndLoginIfSuccess(authorizationHeader.substring("Bearer ".length()));
      }catch (Exception ignore){}
    }
    filterChain.doFilter(request,response);
  }
}
