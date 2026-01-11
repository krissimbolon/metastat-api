package com.bps.metastat.controller;

import com.bps.metastat.dto.response.ApiResponse;
import com.bps.metastat.dto.response.ConceptResponseDTO;
import com.bps.metastat.service.ConceptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concepts")
@RequiredArgsConstructor
@Tag(name = "Concepts (SDMX/GSIM)", description = "SDMX/GSIM-compliant concept endpoints - statistical terminology and concepts")
public class ConceptController {

  private final ConceptService conceptService;

  @GetMapping
  @Operation(
      summary = "List concepts with search",
      description = "Get all concepts or search by term/code. Public endpoint. Supports filtering by term (LIKE query) or code (exact match).")
  public ResponseEntity<ApiResponse<List<ConceptResponseDTO>>> listConcepts(
      @RequestParam(required = false) String term,
      @RequestParam(required = false) String code) {
    List<ConceptResponseDTO> concepts = conceptService.findAll(term, code);
    return ResponseEntity.ok(ApiResponse.success(concepts, "Concepts retrieved successfully"));
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Get concept detail",
      description = "Get detailed information about a specific concept including related variables count. Public endpoint.")
  public ResponseEntity<ApiResponse<ConceptResponseDTO>> getConcept(@PathVariable Long id) {
    ConceptResponseDTO concept = conceptService.findById(id);
    return ResponseEntity.ok(ApiResponse.success(concept, "Concept retrieved successfully"));
  }
}
