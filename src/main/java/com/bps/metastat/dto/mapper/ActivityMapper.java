package com.bps.metastat.dto.mapper;

import com.bps.metastat.domain.entity.*;
import com.bps.metastat.dto.request.ActivityRequestDTO;
import com.bps.metastat.dto.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ActivityMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "activityCode", ignore = true) // Auto-generated
  @Mapping(target = "status", ignore = true) // Set in service
  @Mapping(target = "createdBy", ignore = true) // Set in service
  @Mapping(target = "domain", ignore = true) // Set from domainId
  @Mapping(target = "organization", ignore = true) // Set from organizationId
  @Mapping(target = "subjects", ignore = true) // Set from subjectIds
  @Mapping(target = "variables", ignore = true)
  @Mapping(target = "publications", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  StatisticalActivity toEntity(ActivityRequestDTO dto);

  @Mapping(target = "variableCount", expression = "java(activity.getVariables() != null ? activity.getVariables().size() : 0)")
  @Mapping(target = "publicationCount", expression = "java(activity.getPublications() != null ? activity.getPublications().size() : 0)")
  @Mapping(target = "domain", source = "domain")
  @Mapping(target = "organization", source = "organization")
  @Mapping(target = "subjects", source = "subjects")
  @Mapping(target = "createdBy", source = "createdBy")
  ActivityResponseDTO toResponseDTO(StatisticalActivity activity);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "code", source = "code")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "type", source = "type")
  DomainDTO toDomainDTO(Domain domain);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "type", source = "type")
  OrganizationDTO toOrganizationDTO(Organization organization);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "code", source = "code")
  @Mapping(target = "name", source = "name")
  SubjectDTO toSubjectDTO(SubjectCategory subject);

  ActivityResponseDTO.UserInfo toUserInfo(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "activityCode", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "domain", ignore = true)
  @Mapping(target = "organization", ignore = true)
  @Mapping(target = "subjects", ignore = true)
  @Mapping(target = "variables", ignore = true)
  @Mapping(target = "publications", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateEntityFromDTO(ActivityRequestDTO dto, @MappingTarget StatisticalActivity entity);
}
