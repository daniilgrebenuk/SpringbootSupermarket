package com.project.model.credential;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public enum Authority {
  ADMIN(Set.of(Permission.values())),
  USER(Collections.emptySet());

  private final Set<Permission> permissions;

  Authority(Set<Permission> permissions) {
    this.permissions = permissions;
  }

  public Set<SimpleGrantedAuthority> getAuthorities(){
    return permissions
        .stream()
        .map(permission -> new SimpleGrantedAuthority(permission.name()))
        .collect(Collectors.toSet());
  }
}
