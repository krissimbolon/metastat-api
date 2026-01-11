package com.bps.metastat.controller;

import com.bps.metastat.dto.response.ApiResponse;
import com.bps.metastat.dto.response.DataflowResponseDTO;
import com.bps.metastat.service.DataflowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dataflow")
@RequiredArgsConstructor
@Tag(name = "Dataflow (SDMX)", description = "SDMX-compliant dataflow endpoints - list published activities with variables")
public class DataflowController {

  private final DataflowService dataflowService;

  @GetMapping
  @Operation(
      summary = "List all dataflows",
      description = "Get all dataflows (PUBLISHED activities with variables). Public endpoint following SDMX RESTful API standards.")
  public ResponseEntity<ApiResponse<List<DataflowResponseDTO>>> listDataflows() {
    List<DataflowResponseDTO> dataflows = dataflowService.findAll();
    return ResponseEntity.ok(ApiResponse.success(dataflows, "Dataflows retrieved successfully"));
  }

  @GetMapping("/{activityCode}")
  @Operation(
      summary = "Get dataflow structure",
      description = "Get dataflow structure by activity code. Returns activity metadata + variable structure (dimensions). Public endpoint.")
  public ResponseEntity<ApiResponse<DataflowResponseDTO>> getDataflow(
      @PathVariable String activityCode) {
    DataflowResponseDTO dataflow = dataflowService.findByActivityCode(activityCode);
    return ResponseEntity.ok(ApiResponse.success(dataflow, "Dataflow retrieved successfully"));
  }
}
