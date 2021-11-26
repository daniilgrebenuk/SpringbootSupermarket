package com.project.repository.credential;

import com.project.model.credential.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByMobileNumber(String mobileNumber);
}
