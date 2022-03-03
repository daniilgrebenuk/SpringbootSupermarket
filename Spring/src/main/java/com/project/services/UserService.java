package com.project.services;

import com.project.model.credential.RegistrationForm;
import com.project.model.credential.Role;
import com.project.model.credential.User;

import java.util.List;

public interface UserService {

  User findByUsernameOrMobileNumber(String usernameOrMobileNumber);

  User regNewUserFromRegistrationForm(RegistrationForm form);

  List<Role> findAllRoles();

  User findUserById(Long id);

  User save(User user);

  List<User> findAll();

  void deleteUserById(Long id);

  User update(User user);
}
