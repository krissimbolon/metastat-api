package com.bps.metastat.dto.mapper;

import com.bps.metastat.domain.entity.Variable;
import com.bps.metastat.dto.request.VariableRequestDTO;
import com.bps.metastat.dto.response.VariableResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VariableMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "activity", ignore = true) // Set from activityId
  @Mapping(target = "createdAt", ignore = true)
  Variable toEntity(VariableRequestDTO dto);

  @Mapping(target = "activity.id", source = "activity.id")
  @Mapping(target = "activity.title", source = "activity.title")
  VariableResponseDTO toResponseDTO(Variable variable);
}
