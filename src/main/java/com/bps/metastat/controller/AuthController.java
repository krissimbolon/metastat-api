package com.bps.metastat.controller;

import com.bps.metastat.dto.request.LoginRequest;
import com.bps.metastat.dto.request.RegisterRequest;
import com.bps.metastat.dto.response.ApiResponse;
import com.bps.metastat.dto.response.LoginResponse;
import com.bps.metastat.dto.response.UserResponseDTO;
import com.bps.metastat.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User registration and login endpoints")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  @Operation(summary = "Register new account", description = "Create a new user account")
  public ResponseEntity<ApiResponse<UserResponseDTO>> register(
      @Valid @RequestBody RegisterRequest request) {
    UserResponseDTO userResponse = authService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(userResponse, "User registered successfully"));
  }

  @PostMapping("/login")
  @Operation(summary = "Login & get JWT token", description = "Authenticate user and receive JWT token")
  public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
    LoginResponse loginResponse = authService.login(request);
    return ResponseEntity.ok(ApiResponse.success(loginResponse, "Login successful"));
  }
}
