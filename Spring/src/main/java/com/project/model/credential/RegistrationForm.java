package com.project.model.credential;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationForm {

  @NotNull(message = "Enter username!")
  @Size(min = 4, message = "Username must be at least 4 characters")
  private String username;

  @NotNull(message = "Enter password!")
  @Size(min = 4, message = "Password must be at least 4 characters")
  private String password;
}
