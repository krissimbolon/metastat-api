package com.bps.metastat.controller;

import com.bps.metastat.dto.response.ApiResponse;
import com.bps.metastat.dto.response.MetadataResponseDTO;
import com.bps.metastat.service.MetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metadata")
@RequiredArgsConstructor
@Tag(name = "Metadata (SDMX/GSIM)", description = "SDMX/GSIM-compliant metadata endpoints - reference metadata for activities")
public class MetadataController {

  private final MetadataService metadataService;

  @GetMapping("/activity/{activityCode}")
  @Operation(
      summary = "Get reference metadata",
      description = "Get reference metadata for an activity including methodology, coverage, and quality information. Public endpoint.")
  public ResponseEntity<ApiResponse<MetadataResponseDTO>> getActivityMetadata(
      @PathVariable String activityCode) {
    MetadataResponseDTO metadata = metadataService.getActivityMetadata(activityCode);
    return ResponseEntity.ok(ApiResponse.success(metadata, "Metadata retrieved successfully"));
  }
}
