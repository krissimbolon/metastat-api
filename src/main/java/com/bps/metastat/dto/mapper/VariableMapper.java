package com.bps.metastat.dto.mapper;

import com.bps.metastat.domain.entity.StatisticalActivity;
import com.bps.metastat.domain.entity.Variable;
import com.bps.metastat.dto.request.VariableRequestDTO;
import com.bps.metastat.dto.response.VariableResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VariableMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "activity", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  Variable toEntity(VariableRequestDTO dto);

  @Mapping(target = "activity", source = "activity", qualifiedByName = "toActivitySummary")
  VariableResponseDTO toResponseDTO(Variable variable);

  @Named("toActivitySummary")
  default VariableResponseDTO.ActivitySummaryDTO toActivitySummary(StatisticalActivity activity) {
    if (activity == null) return null;
    return VariableResponseDTO.ActivitySummaryDTO.builder()
            .id(activity.getId())
            .title(activity.getTitle())
            .build();
  }
}
