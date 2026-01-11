package com.bps.metastat.dto.mapper;

import com.bps.metastat.domain.entity.Publication;
import com.bps.metastat.domain.entity.StatisticalActivity;
import com.bps.metastat.dto.request.PublicationRequestDTO;
import com.bps.metastat.dto.response.PublicationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PublicationMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "activity", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  Publication toEntity(PublicationRequestDTO dto);

  @Mapping(target = "activity", source = "activity", qualifiedByName = "toActivitySummary")
  PublicationResponseDTO toResponseDTO(Publication publication);

  @Named("toActivitySummary")
  default PublicationResponseDTO.ActivitySummaryDTO toActivitySummary(StatisticalActivity activity) {
    if (activity == null) return null;
    return PublicationResponseDTO.ActivitySummaryDTO.builder()
            .id(activity.getId())
            .title(activity.getTitle())
            .build();
  }
}
