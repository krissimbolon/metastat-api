package com.bps.metastat.dto.mapper;

import com.bps.metastat.domain.entity.Publication;
import com.bps.metastat.dto.request.PublicationRequestDTO;
import com.bps.metastat.dto.response.PublicationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PublicationMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "activity", ignore = true) // Set from activityId
  @Mapping(target = "createdAt", ignore = true)
  Publication toEntity(PublicationRequestDTO dto);

  @Mapping(target = "activity.id", source = "activity.id")
  @Mapping(target = "activity.title", source = "activity.title")
  PublicationResponseDTO toResponseDTO(Publication publication);
}
