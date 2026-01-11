package com.bps.metastat.dto.request;

import com.bps.metastat.domain.enums.VariableDataType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariableRequestDTO {

  @NotBlank(message = "Variable name cannot be empty")
  @Size(max = 300, message = "Variable name cannot exceed 300 characters")
  private String name;

  @Size(max = 50, message = "Alias cannot exceed 50 characters")
  private String alias;

  @Size(max = 2000, message = "Definition cannot exceed 2000 characters")
  private String definition;

  private String questionText;
  private VariableDataType dataType;
  private String referencePeriod;

  @NotNull(message = "Activity ID cannot be null")
  private Long activityId;
}
