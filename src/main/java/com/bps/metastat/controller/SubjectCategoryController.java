package com.bps.metastat.controller;

import com.bps.metastat.dto.response.ApiResponse;
import com.bps.metastat.dto.response.SubjectDTO;
import com.bps.metastat.service.SubjectCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
@Tag(name = "Subject Categories", description = "Statistical subject classification")
public class SubjectCategoryController {

    private final SubjectCategoryService subjectCategoryService;

    @GetMapping
    @Operation(
        summary = "List all subject categories",
        description = "Get all subject categories. Public endpoint."
    )
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> listSubjects() {
        List<SubjectDTO> subjects = subjectCategoryService.findAll();
        return ResponseEntity.ok(
            ApiResponse.success(subjects, "Subjects retrieved successfully")
        );
    }
}
