package com.bps.metastat.controller;

import com.bps.metastat.domain.entity.User;
import com.bps.metastat.dto.request.PublicationRequestDTO;
import com.bps.metastat.dto.response.ApiResponse;
import com.bps.metastat.dto.response.PublicationResponseDTO;
import com.bps.metastat.service.PublicationService;
import com.bps.metastat.service.UserService;
import com.bps.metastat.domain.entity.Publication;
import com.bps.metastat.domain.repository.PublicationRepository;
import com.bps.metastat.exception.EntityNotFoundException;
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
@RequestMapping("/api/publications")
@RequiredArgsConstructor
@Tag(name = "Publications", description = "Manage publications for statistical activities")
public class PublicationController {

  private final PublicationService publicationService;
  private final UserService userService;
  private final PublicationRepository publicationRepository;

  @GetMapping("/activities/{activityId}")
  @Operation(summary = "List publications by activity", description = "Get all publications for a specific activity. Public endpoint.")
  public ResponseEntity<ApiResponse<List<PublicationResponseDTO>>> listPublicationsByActivity(
      @PathVariable Long activityId) {

    List<PublicationResponseDTO> result = publicationService.findByActivity(activityId);
    return ResponseEntity.ok(ApiResponse.success(result, "Publications retrieved successfully"));
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  @Operation(
      summary = "Create publication",
      description = "Create a new publication for an activity. Requires authentication and ownership of the activity.",
      security = @SecurityRequirement(name = "Bearer Authentication"))
  public ResponseEntity<ApiResponse<PublicationResponseDTO>> createPublication(
      @Valid @RequestBody PublicationRequestDTO request,
      @AuthenticationPrincipal UserDetails currentUser) {

    User user = userService.getCurrentUserEntity(currentUser.getUsername());
    PublicationResponseDTO result = publicationService.create(request, user);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(result, "Publication created successfully"));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  @Operation(
      summary = "Delete publication",
      description = "Delete a publication. Requires authentication and ownership of the parent activity.",
      security = @SecurityRequirement(name = "Bearer Authentication"))
  public ResponseEntity<Void> deletePublication(
      @PathVariable Long id,
      @AuthenticationPrincipal UserDetails currentUser) {

    User user = userService.getCurrentUserEntity(currentUser.getUsername());
    publicationService.delete(id, user);

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  @Operation(
      summary = "Update publication",
      description = "Update publication metadata. Requires authentication and ownership.",
      security = @SecurityRequirement(name = "Bearer Authentication"))
  public ResponseEntity<ApiResponse<PublicationResponseDTO>> updatePublication(
      @PathVariable Long id,
      @Valid @RequestBody PublicationRequestDTO request,
      @AuthenticationPrincipal UserDetails currentUser) {

    User user = userService.getCurrentUserEntity(currentUser.getUsername());
    PublicationResponseDTO result = publicationService.update(id, request, user);

    return ResponseEntity.ok(ApiResponse.success(result, "Publication updated successfully"));
  }

  @GetMapping("/{id}/download")
  @Operation(
      summary = "Get download URL",
      description = "Get publication download URL. Public endpoint.")
  public ResponseEntity<ApiResponse<String>> getDownloadUrl(@PathVariable Long id) {
    // For now, just return the URL from database
    // In production, this could serve actual files or redirect
    Publication publication = publicationRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Publication not found"));
    
    return ResponseEntity.ok(
        ApiResponse.success(publication.getDownloadUrl(), "Download URL retrieved")
    );
  }

}
