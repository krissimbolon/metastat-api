package com.bps.metastat.service;

import com.bps.metastat.domain.entity.User;
import com.bps.metastat.domain.repository.UserRepository;
import com.bps.metastat.dto.request.LoginRequest;
import com.bps.metastat.dto.request.RegisterRequest;
import com.bps.metastat.dto.response.LoginResponse;
import com.bps.metastat.dto.response.UserResponseDTO;
import com.bps.metastat.exception.InvalidCredentialsException;
import com.bps.metastat.exception.DuplicateEmailException;
import com.bps.metastat.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional
  public UserResponseDTO register(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new DuplicateEmailException("Email already exists: " + request.getEmail());
    }

    User user =
        User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .fullname(request.getFullname())
            .role(request.getRole())
            .active(true)
            .build();

    user = userRepository.save(user);

    return UserResponseDTO.builder()
        .id(user.getId())
        .email(user.getEmail())
        .fullname(user.getFullname())
        .role(user.getRole())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
  }

  public LoginResponse login(LoginRequest request) {
    User user =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

    if (!user.getActive()) {
      throw new InvalidCredentialsException("User account is inactive");
    }

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new InvalidCredentialsException("Invalid email or password");
    }

    String token =
        jwtTokenProvider.generateToken(
            user.getEmail(), user.getId(), user.getRole().name());

    LoginResponse.UserInfo userInfo =
        LoginResponse.UserInfo.builder()
            .id(user.getId())
            .email(user.getEmail())
            .fullname(user.getFullname())
            .role(user.getRole())
            .build();

    return LoginResponse.builder()
        .token(token)
        .type("Bearer")
        .expiresIn(jwtTokenProvider.getJwtExpiration() / 1000) // Convert to seconds
        .user(userInfo)
        .build();
  }

  // Make jwtExpiration accessible for LoginResponse
  public Long getJwtExpirationInSeconds() {
    return jwtTokenProvider.getJwtExpiration() / 1000;
  }
}
