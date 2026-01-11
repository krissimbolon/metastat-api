package com.bps.metastat.dto.request;

import com.bps.metastat.domain.enums.CollectionTechnique;
import com.bps.metastat.domain.enums.DataCollectionMethod;
import com.bps.metastat.domain.enums.Frequency;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityRequestDTO {

  @NotBlank(message = "Title cannot be empty")
  @Size(max = 500, message = "Title cannot exceed 500 characters")
  private String title;

  @NotNull(message = "Year cannot be null")
  @Min(value = 1900, message = "Year must be >= 1900")
  @Max(value = 2100, message = "Year must be <= 2100")
  private Integer year;

  @Size(max = 5000, message = "Description cannot exceed 5000 characters")
  private String description;

  @Size(max = 5000, message = "Background cannot exceed 5000 characters")
  private String background;

  @Size(max = 5000, message = "Objectives cannot exceed 5000 characters")
  private String objectives;

  private DataCollectionMethod collectionMethod;
  private Frequency frequency;
  private Set<CollectionTechnique> collectionTechniques;
  private String coverageArea;
  private String referencePeriod;
  private String samplingMethod;
  private Integer sampleSize;
  private Boolean isPublic;
  private LocalDate releaseDate;

  @NotNull(message = "Domain ID cannot be null")
  private Long domainId;

  @NotNull(message = "Organization ID cannot be null")
  private Long organizationId;

  @NotEmpty(message = "At least one subject is required")
  private Set<Long> subjectIds;
}
