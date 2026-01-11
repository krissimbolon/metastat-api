package com.bps.metastat.dto.response;

import com.bps.metastat.domain.enums.DomainType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DomainDTO {
  private Long id;
  private String code;
  private String name;
  private DomainType type;
}
