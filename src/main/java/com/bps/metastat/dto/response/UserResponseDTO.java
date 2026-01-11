package com.bps.metastat.dto.response;

import com.bps.metastat.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
  private Long id;
  private String email;
  private String fullname;
  private UserRole role;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
