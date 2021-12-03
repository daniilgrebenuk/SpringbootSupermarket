package com.project.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.model.credential.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.token.access}")
  private long expireAccessTokenInMinute;

  @Value("${jwt.token.refresh}")
  private long expireRefreshTokenInDay;

  public Map<String, String> createToken(User user, String requestUrl){
    Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
    String accessToken = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + expireAccessTokenInMinute * 60 * 1000))
        .withIssuer(requestUrl)
        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .sign(algorithm);

    String refreshToken = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + expireRefreshTokenInDay*60*1000))
        .withIssuer(requestUrl)
        .sign(algorithm);

    Map<String,String> map = new HashMap<>();
    map.put("accessToken", accessToken);
    map.put("refreshToken", refreshToken);
    return map;
  }

  public void verifyTokenAndLoginIfSuccess(String token){
    Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT decodedJWT = verifier.verify(token);
    String username = decodedJWT.getSubject();
    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(
            username,
            null,
            Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        )
    );
  }
}
