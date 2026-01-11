package com.bps.metastat.dto.request;

import com.bps.metastat.domain.enums.ActivityStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusUpdateDTO {
  @NotNull(message = "Status cannot be null")
  private ActivityStatus status;
}
