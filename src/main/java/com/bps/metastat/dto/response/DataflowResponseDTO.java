package com.bps.metastat.dto.response;

import com.bps.metastat.domain.enums.VariableDataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataflowResponseDTO {
  private String flowRef; // "{organizationCode},{activityCode},1.0"
  private String activityCode;
  private String title;
  private Integer year;
  private OrganizationDTO organization;
  private Integer variableCount;
  private List<VariableDTO> structure; // dimensions

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class VariableDTO {
    private Long id;
    private String name;
    private String alias;
    private String definition;
    private VariableDataType dataType;
    private String referencePeriod;
  }
}
