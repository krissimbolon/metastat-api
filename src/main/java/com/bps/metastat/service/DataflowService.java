package com.bps.metastat.service;

import com.bps.metastat.domain.entity.StatisticalActivity;
import com.bps.metastat.domain.enums.ActivityStatus;
import com.bps.metastat.domain.repository.StatisticalActivityRepository;
import com.bps.metastat.dto.mapper.ActivityMapper;
import com.bps.metastat.dto.response.DataflowResponseDTO;
import com.bps.metastat.dto.response.OrganizationDTO;
import com.bps.metastat.dto.response.VariableResponseDTO;
import com.bps.metastat.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DataflowService {

  private final StatisticalActivityRepository activityRepository;
  private final VariableService variableService;
  private final ActivityMapper activityMapper;

  @Transactional(readOnly = true)
  public List<DataflowResponseDTO> findAll() {
    // Dataflow = PUBLISHED activities with variables
    Pageable unpaged = PageRequest.of(0, Integer.MAX_VALUE);
    List<StatisticalActivity> publishedActivities = activityRepository.findByStatus(ActivityStatus.PUBLISHED, unpaged)
        .getContent()
        .stream()
        .filter(activity -> activity.getVariables() != null && !activity.getVariables().isEmpty())
        .collect(Collectors.toList());

    return publishedActivities.stream()
        .map(this::toDataflowDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public DataflowResponseDTO findByActivityCode(String activityCode) {
    StatisticalActivity activity = activityRepository.findByActivityCode(activityCode)
        .orElseThrow(() -> new EntityNotFoundException("Dataflow not found with activityCode: " + activityCode));

    // Ensure it's PUBLISHED and has variables
    if (activity.getStatus() != ActivityStatus.PUBLISHED) {
      throw new EntityNotFoundException("Dataflow not found (activity is not PUBLISHED): " + activityCode);
    }

    if (activity.getVariables() == null || activity.getVariables().isEmpty()) {
      throw new EntityNotFoundException("Dataflow not found (activity has no variables): " + activityCode);
    }

    return toDataflowDTO(activity);
  }

  private DataflowResponseDTO toDataflowDTO(StatisticalActivity activity) {
    // Generate flowRef: "{organizationId},{activityCode},1.0"
    // In SDMX format: agencyId,flowId,version
    String organizationId = activity.getOrganization().getId().toString();
    String flowRef = String.format("%s,%s,1.0", organizationId, activity.getActivityCode());

    // Get variables as structure
    List<VariableResponseDTO> variables = variableService.findByActivity(activity.getId());
    List<DataflowResponseDTO.VariableDTO> structure = variables.stream()
        .map(v -> DataflowResponseDTO.VariableDTO.builder()
            .id(v.getId())
            .name(v.getName())
            .alias(v.getAlias())
            .definition(v.getDefinition())
            .dataType(v.getDataType())
            .referencePeriod(v.getReferencePeriod())
            .build())
        .collect(Collectors.toList());

    OrganizationDTO organizationDTO = activityMapper.toOrganizationDTO(activity.getOrganization());

    return DataflowResponseDTO.builder()
        .flowRef(flowRef)
        .activityCode(activity.getActivityCode())
        .title(activity.getTitle())
        .year(activity.getYear())
        .organization(organizationDTO)
        .variableCount(activity.getVariables() != null ? activity.getVariables().size() : 0)
        .structure(structure)
        .build();
  }
}
