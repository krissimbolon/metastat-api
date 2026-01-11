package com.bps.metastat.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

  @NotBlank(message = "Email cannot be empty")
  @Email(message = "Email must be valid")
  private String email;

  @NotBlank(message = "Password cannot be empty")
  private String password;
}
