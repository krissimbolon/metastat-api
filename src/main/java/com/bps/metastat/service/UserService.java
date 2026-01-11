package com.bps.metastat.service;

import com.bps.metastat.domain.entity.User;
import com.bps.metastat.domain.repository.UserRepository;
import com.bps.metastat.dto.response.UserResponseDTO;
import com.bps.metastat.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public UserResponseDTO getCurrentUser(String email) {
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

    return UserResponseDTO.builder()
        .id(user.getId())
        .email(user.getEmail())
        .fullname(user.getFullname())
        .role(user.getRole())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
  }

  @Transactional(readOnly = true)
  public User getCurrentUserEntity(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));
  }
}
