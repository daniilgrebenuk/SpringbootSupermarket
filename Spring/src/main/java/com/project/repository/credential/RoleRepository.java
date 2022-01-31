package com.project.repository.credential;

import com.project.model.credential.Authority;
import com.project.model.credential.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByAuthority(Authority authority);
  Boolean existsByAuthority(Authority authority);
}
