package com.bps.metastat.dto.response;

import com.bps.metastat.domain.enums.ActivityStatus;
import com.bps.metastat.domain.enums.CollectionTechnique;
import com.bps.metastat.domain.enums.DataCollectionMethod;
import com.bps.metastat.domain.enums.Frequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityResponseDTO {
  private Long id;
  private String activityCode;
  private String title;
  private Integer year;
  private ActivityStatus status;
  private String description;
  private String background;
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

  // Nested DTOs
  private DomainDTO domain;
  private OrganizationDTO organization;
  private Set<SubjectDTO> subjects;

  // Counts instead of full lists
  private Integer variableCount;
  private Integer publicationCount;

  // Creator info
  private UserInfo createdBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UserInfo {
    private Long id;
    private String fullname;
  }
}
