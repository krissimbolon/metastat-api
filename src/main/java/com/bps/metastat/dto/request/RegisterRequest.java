package com.bps.metastat.dto.request;

import com.bps.metastat.domain.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "Email cannot be empty")
  @Email(message = "Email must be valid")
  @Size(max = 100, message = "Email cannot exceed 100 characters")
  private String email;

  @NotBlank(message = "Password cannot be empty")
  @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
  private String password;

  @NotBlank(message = "Fullname cannot be empty")
  @Size(max = 200, message = "Fullname cannot exceed 200 characters")
  private String fullname;

  @NotNull(message = "Role cannot be null")
  private UserRole role;
}
