package com.bps.metastat.service;

import com.bps.metastat.domain.entity.StatisticalActivity;
import com.bps.metastat.domain.entity.User;
import com.bps.metastat.domain.entity.Variable;
import com.bps.metastat.domain.enums.UserRole;
import com.bps.metastat.domain.repository.StatisticalActivityRepository;
import com.bps.metastat.domain.repository.VariableRepository;
import com.bps.metastat.dto.mapper.VariableMapper;
import com.bps.metastat.dto.request.VariableRequestDTO;
import com.bps.metastat.dto.response.VariableResponseDTO;
import com.bps.metastat.exception.EntityNotFoundException;
import com.bps.metastat.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VariableService {

  private final VariableRepository variableRepository;
  private final StatisticalActivityRepository activityRepository;
  private final VariableMapper variableMapper;

  @Transactional(readOnly = true)
  public List<VariableResponseDTO> findByActivity(Long activityId) {
    StatisticalActivity activity = activityRepository.findById(activityId)
        .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + activityId));

    List<Variable> variables = variableRepository.findByActivity(activity);
    return variables.stream()
        .map(variableMapper::toResponseDTO)
        .collect(Collectors.toList());
  }

  public VariableResponseDTO create(VariableRequestDTO dto, User currentUser) {
    StatisticalActivity activity = activityRepository.findById(dto.getActivityId())
        .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + dto.getActivityId()));

    // Ownership check
    if (!activity.getCreatedBy().getId().equals(currentUser.getId())
        && currentUser.getRole() != UserRole.ADMIN) {
      throw new ForbiddenException("Cannot add variable to activity you don't own");
    }

    Variable variable = variableMapper.toEntity(dto);
    variable.setActivity(activity);
    variable = variableRepository.save(variable);

    return variableMapper.toResponseDTO(variable);
  }

  public void delete(Long id, User currentUser) {
    Variable variable = variableRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Variable not found with id: " + id));

    StatisticalActivity activity = variable.getActivity();

    // Ownership check
    if (!activity.getCreatedBy().getId().equals(currentUser.getId())
        && currentUser.getRole() != UserRole.ADMIN) {
      throw new ForbiddenException("Cannot delete variable from activity you don't own");
    }

    variableRepository.delete(variable);
  }
}
