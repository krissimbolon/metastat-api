package com.bps.metastat.controller;

import com.bps.metastat.dto.response.ApiResponse;
import com.bps.metastat.dto.response.OrganizationDTO;
import com.bps.metastat.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
@Tag(name = "Organizations", description = "Statistical organizations (NSO hierarchy)")
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    @Operation(
        summary = "List all organizations",
        description = "Get all organizations. Public endpoint."
    )
    public ResponseEntity<ApiResponse<List<OrganizationDTO>>> listOrganizations() {
        List<OrganizationDTO> organizations = organizationService.findAll();
        return ResponseEntity.ok(
            ApiResponse.success(organizations, "Organizations retrieved successfully")
        );
    }
}
