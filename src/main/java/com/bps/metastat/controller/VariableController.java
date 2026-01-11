package com.bps.metastat.controller;

import com.bps.metastat.domain.entity.User;
import com.bps.metastat.dto.request.VariableRequestDTO;
import com.bps.metastat.dto.response.ApiResponse;
import com.bps.metastat.dto.response.VariableResponseDTO;
import com.bps.metastat.service.UserService;
import com.bps.metastat.service.VariableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/variables")
@RequiredArgsConstructor
@Tag(name = "Variables", description = "Manage variables (questions) for statistical activities")
public class VariableController {

  private final VariableService variableService;
  private final UserService userService;

  @GetMapping("/activities/{activityId}")
  @Operation(summary = "List variables by activity", description = "Get all variables for a specific activity. Public endpoint.")
  public ResponseEntity<ApiResponse<List<VariableResponseDTO>>> listVariablesByActivity(
      @PathVariable Long activityId) {

    List<VariableResponseDTO> result = variableService.findByActivity(activityId);
    return ResponseEntity.ok(ApiResponse.success(result, "Variables retrieved successfully"));
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  @Operation(
      summary = "Create variable",
      description = "Create a new variable for an activity. Requires authentication and ownership of the activity.",
      security = @SecurityRequirement(name = "Bearer Authentication"))
  public ResponseEntity<ApiResponse<VariableResponseDTO>> createVariable(
      @Valid @RequestBody VariableRequestDTO request,
      @AuthenticationPrincipal UserDetails currentUser) {

    User user = userService.getCurrentUserEntity(currentUser.getUsername());
    VariableResponseDTO result = variableService.create(request, user);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(result, "Variable created successfully"));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  @Operation(
      summary = "Delete variable",
      description = "Delete a variable. Requires authentication and ownership of the parent activity.",
      security = @SecurityRequirement(name = "Bearer Authentication"))
  public ResponseEntity<Void> deleteVariable(
      @PathVariable Long id,
      @AuthenticationPrincipal UserDetails currentUser) {

    User user = userService.getCurrentUserEntity(currentUser.getUsername());
    variableService.delete(id, user);

    return ResponseEntity.noContent().build();
  }
}
