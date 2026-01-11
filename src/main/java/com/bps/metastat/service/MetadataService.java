package com.bps.metastat.service;

import com.bps.metastat.domain.entity.StatisticalActivity;
import com.bps.metastat.domain.repository.StatisticalActivityRepository;
import com.bps.metastat.dto.mapper.ActivityMapper;
import com.bps.metastat.dto.response.DomainDTO;
import com.bps.metastat.dto.response.MetadataResponseDTO;
import com.bps.metastat.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MetadataService {

  private final StatisticalActivityRepository activityRepository;
  private final ActivityMapper activityMapper;

  @Transactional(readOnly = true)
  public MetadataResponseDTO getActivityMetadata(String activityCode) {
    StatisticalActivity activity = activityRepository.findByActivityCode(activityCode)
        .orElseThrow(() -> new EntityNotFoundException("Activity not found with code: " + activityCode));

    DomainDTO domainDTO = activityMapper.toDomainDTO(activity.getDomain());

    MetadataResponseDTO.MethodologyDTO methodology = MetadataResponseDTO.MethodologyDTO.builder()
        .samplingMethod(activity.getSamplingMethod())
        .collectionTechniques(activity.getCollectionTechniques())
        .sampleSize(activity.getSampleSize())
        .build();

    MetadataResponseDTO.CoverageDTO coverage = MetadataResponseDTO.CoverageDTO.builder()
        .domain(domainDTO)
        .referencePeriod(activity.getReferencePeriod())
        .build();

    MetadataResponseDTO.QualityDTO quality = MetadataResponseDTO.QualityDTO.builder()
        .variableCount(activity.getVariables() != null ? activity.getVariables().size() : 0)
        .publicationCount(activity.getPublications() != null ? activity.getPublications().size() : 0)
        .build();

    return MetadataResponseDTO.builder()
        .metadataTarget(activityCode)
        .methodology(methodology)
        .coverage(coverage)
        .quality(quality)
        .build();
  }
}
