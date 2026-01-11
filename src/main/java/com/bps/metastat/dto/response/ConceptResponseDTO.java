package com.bps.metastat.dto.response;

import com.bps.metastat.domain.enums.VariableDataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConceptResponseDTO {
  private Long id;
  private String code; // alias or name-based code
  private String name;
  private String definition;
  private VariableDataType dataType;
  private Integer relatedVariablesCount; // count of variables using this concept
}
