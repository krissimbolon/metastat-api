package com.bps.metastat.controller;

import com.bps.metastat.domain.entity.User;
import com.bps.metastat.domain.enums.ActivityStatus;
import com.bps.metastat.dto.request.ActivityRequestDTO;
import com.bps.metastat.service.UserService;
import com.bps.metastat.dto.request.StatusUpdateDTO;
import com.bps.metastat.dto.response.ActivityResponseDTO;
import com.bps.metastat.dto.response.ApiResponse;
import com.bps.metastat.dto.response.PagedResponse;
import com.bps.metastat.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
@Tag(name = "Statistical Activities", description = "Manage statistical activities (surveys, census)")
public class ActivityController {

  private final ActivityService activityService;
  private final UserService userService;

  @GetMapping
  @Operation(summary = "List all statistical activities", description = "Get paginated list of activities with optional filters. Public endpoint - PUBLISHED activities visible to all, DRAFT only to owner.")
  public ResponseEntity<ApiResponse<PagedResponse<ActivityResponseDTO>>> listActivities(
      @RequestParam(required = false) Long domainId,
      @RequestParam(required = false) Long subjectId,
      @RequestParam(required = false) ActivityStatus status,
      @RequestParam(required = false) Integer year,
      @RequestParam(required = false) String search,
      @PageableDefault(size = 20, sort = "year", direction = Sort.Direction.DESC) Pageable pageable,
      @AuthenticationPrincipal UserDetails currentUser) {

    User user = currentUser != null ? userService.getCurrentUserEntity(currentUser.getUsername()) : null;
    PagedResponse<ActivityResponseDTO> result =
        activityService.findAll(domainId, subjectId, status, year, search, pageable, user);

    return ResponseEntity.ok(ApiResponse.success(result, "Data retrieved successfully"));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get activity detail", description = "Get detailed information about a specific activity. PUBLISHED activities are public, DRAFT requires authentication and ownership.")
  public ResponseEntity<ApiResponse<ActivityResponseDTO>> getActivity(
      @PathVariable Long id,
      @AuthenticationPrincipal UserDetails currentUser) {

    User user = currentUser != null ? userService.getCurrentUserEntity(currentUser.getUsername()) : null;
    ActivityResponseDTO result = activityService.findById(id, user);

    return ResponseEntity.ok(ApiResponse.success(result));
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  @Operation(
      summary = "Create new activity",
      description = "Create a new statistical activity (requires authentication). Creates as DRAFT status.",
      security = @SecurityRequirement(name = "Bearer Authentication"))
  public ResponseEntity<ApiResponse<ActivityResponseDTO>> createActivity(
      @Valid @RequestBody ActivityRequestDTO request,
      @AuthenticationPrincipal UserDetails currentUser) {

    User user = userService.getCurrentUserEntity(currentUser.getUsername());
    ActivityResponseDTO result = activityService.create(request, user);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(result, "Activity created successfully"));
  }

  @PutMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  @Operation(
      summary = "Update activity",
      description = "Update an existing activity. Only owner or ADMIN can update.",
      security = @SecurityRequirement(name = "Bearer Authentication"))
  public ResponseEntity<ApiResponse<ActivityResponseDTO>> updateActivity(
      @PathVariable Long id,
      @Valid @RequestBody ActivityRequestDTO request,
      @AuthenticationPrincipal UserDetails currentUser) {

    User user = userService.getCurrentUserEntity(currentUser.getUsername());
    ActivityResponseDTO result = activityService.update(id, request, user);

    return ResponseEntity.ok(ApiResponse.success(result, "Activity updated successfully"));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  @Operation(
      summary = "Delete activity",
      description = "Delete an activity. Only owner or ADMIN can delete. Cascade deletes variables and publications.",
      security = @SecurityRequirement(name = "Bearer Authentication"))
  public ResponseEntity<Void> deleteActivity(
      @PathVariable Long id,
      @AuthenticationPrincipal UserDetails currentUser) {

    User user = userService.getCurrentUserEntity(currentUser.getUsername());
    activityService.delete(id, user);

    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/status")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      summary = "Change activity status",
      description = "Change activity status (PUBLISH/DRAFT). Only ADMIN can publish.",
      security = @SecurityRequirement(name = "Bearer Authentication"))
  public ResponseEntity<ApiResponse<ActivityResponseDTO>> changeStatus(
      @PathVariable Long id,
      @Valid @RequestBody StatusUpdateDTO statusDTO,
      @AuthenticationPrincipal UserDetails currentUser) {

    User user = userService.getCurrentUserEntity(currentUser.getUsername());
    ActivityResponseDTO result = activityService.changeStatus(id, statusDTO.getStatus(), user);

    String message =
        String.format("Activity status updated to %s", statusDTO.getStatus().name());
    return ResponseEntity.ok(ApiResponse.success(result, message));
  }
}
