package com.bps.metastat.dto.response;

import com.bps.metastat.domain.enums.OrganizationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationDTO {
  private Long id;
  private String name;
  private OrganizationType type;
}
