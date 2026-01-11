package com.bps.metastat.dto.response;

import com.bps.metastat.domain.enums.CollectionTechnique;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetadataResponseDTO {
  private String metadataTarget;
  private MethodologyDTO methodology;
  private CoverageDTO coverage;
  private QualityDTO quality;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class MethodologyDTO {
    private String samplingMethod;
    private Set<CollectionTechnique> collectionTechniques;
    private Integer sampleSize;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CoverageDTO {
    private DomainDTO domain;
    private String referencePeriod;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class QualityDTO {
    private Integer variableCount;
    private Integer publicationCount;
  }
}
