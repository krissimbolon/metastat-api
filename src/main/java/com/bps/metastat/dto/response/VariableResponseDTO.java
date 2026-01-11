package com.bps.metastat.dto.response;

import com.bps.metastat.domain.enums.VariableDataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariableResponseDTO {
  private Long id;
  private String name;
  private String alias;
  private String definition;
  private String questionText;
  private VariableDataType dataType;
  private String referencePeriod;

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
