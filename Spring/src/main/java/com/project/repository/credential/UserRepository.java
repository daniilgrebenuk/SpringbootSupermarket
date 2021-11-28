package com.project.repository.credential;

import com.project.model.credential.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  @Query(
      value = "SELECT u.* " +
              "FROM user u JOIN customer c ON u.id = c.user_id " +
              "WHERE c.mobile_number = ?1",
      nativeQuery = true
  )
  Optional<User> findByMobileNumber(String mobileNumber);
  Optional<User> findByUsername(String username);
}
