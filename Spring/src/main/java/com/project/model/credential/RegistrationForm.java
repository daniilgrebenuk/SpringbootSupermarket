package com.project.model.credential;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationForm {

  @NotNull(message = "Enter username!")
  @Size(min = 4, message = "Username must be at least 4 characters")
  private String username;

  @NotNull(message = "Enter password!")
  @Size(min = 4, message = "Password must be at least 4 characters")
  private String password;
}
