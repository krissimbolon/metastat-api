package com.bps.metastat.controller;

import com.bps.metastat.dto.response.ApiResponse;
import com.bps.metastat.dto.response.UserResponseDTO;
import com.bps.metastat.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User profile management endpoints")
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  @Operation(
      summary = "Get current user profile",
      description = "Get authenticated user's profile information",
      security = @SecurityRequirement(name = "Bearer Authentication"))
  public ResponseEntity<ApiResponse<UserResponseDTO>> getCurrentUser(
      Authentication authentication) {
    String email = ((UserDetails) authentication.getPrincipal()).getUsername();
    UserResponseDTO userResponse = userService.getCurrentUser(email);
    return ResponseEntity.ok(ApiResponse.success(userResponse));
  }
}
