package com.bps.metastat.controller;

import com.bps.metastat.dto.response.ApiResponse;
import com.bps.metastat.dto.response.DomainDTO;
import com.bps.metastat.service.DomainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/domains")
@RequiredArgsConstructor
@Tag(name = "Domains", description = "Geographic domain classification (National, Provincial, District)")
public class DomainController {

    private final DomainService domainService;

    @GetMapping
    @Operation(
        summary = "List all domains",
        description = "Get all geographic domains. Public endpoint."
    )
    public ResponseEntity<ApiResponse<List<DomainDTO>>> listDomains() {
        List<DomainDTO> domains = domainService.findAll();
        return ResponseEntity.ok(
            ApiResponse.success(domains, "Domains retrieved successfully")
        );
    }
}
