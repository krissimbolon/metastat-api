package com.bps.metastat.dto.response;

import com.bps.metastat.domain.enums.Language;
import com.bps.metastat.domain.enums.PublicationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationResponseDTO {
  private Long id;
  private String title;
  private String isbn;
  private String catalogNumber;
  private LocalDate releaseDate;
  private String downloadUrl;
  private PublicationType type;
  private Language language;

  // Nested Activity DTO (id + title only)
  private ActivitySummaryDTO activity;
  private LocalDateTime createdAt;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ActivitySummaryDTO {
    private Long id;
    private String title;
  }
}
