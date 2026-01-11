package com.bps.metastat.service;

import com.bps.metastat.domain.entity.Publication;
import com.bps.metastat.domain.entity.StatisticalActivity;
import com.bps.metastat.domain.entity.User;
import com.bps.metastat.domain.enums.UserRole;
import com.bps.metastat.domain.repository.PublicationRepository;
import com.bps.metastat.domain.repository.StatisticalActivityRepository;
import com.bps.metastat.dto.mapper.PublicationMapper;
import com.bps.metastat.dto.request.PublicationRequestDTO;
import com.bps.metastat.dto.response.PublicationResponseDTO;
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
public class PublicationService {

  private final PublicationRepository publicationRepository;
  private final StatisticalActivityRepository activityRepository;
  private final PublicationMapper publicationMapper;

  @Transactional(readOnly = true)
  public List<PublicationResponseDTO> findByActivity(Long activityId) {
    StatisticalActivity activity = activityRepository.findById(activityId)
            .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + activityId));

    List<Publication> publications = publicationRepository.findByActivity(activity);
    return publications.stream()
            .map(publicationMapper::toResponseDTO)
            .collect(Collectors.toList());
  }

  public PublicationResponseDTO create(PublicationRequestDTO dto, User currentUser) {
    StatisticalActivity activity = activityRepository.findById(dto.getActivityId())
            .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + dto.getActivityId()));

    // Ownership check
    if (!activity.getCreatedBy().getId().equals(currentUser.getId())
            && currentUser.getRole() != UserRole.ADMIN) {
      throw new ForbiddenException("Cannot add publication to activity you don't own");
    }

    Publication publication = publicationMapper.toEntity(dto);
    publication.setActivity(activity);
    publication = publicationRepository.save(publication);

    return publicationMapper.toResponseDTO(publication);
  }

  public PublicationResponseDTO update(Long id, PublicationRequestDTO dto, User currentUser) {
    Publication publication = publicationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Publication not found with id: " + id));

    StatisticalActivity activity = publication.getActivity();

    // Ownership check
    if (!activity.getCreatedBy().getId().equals(currentUser.getId())
            && currentUser.getRole() != UserRole.ADMIN) {
      throw new ForbiddenException("Cannot update publication from activity you don't own");
    }

    // Update fields
    publication.setTitle(dto.getTitle());
    publication.setIsbn(dto.getIsbn());
    publication.setCatalogNumber(dto.getCatalogNumber());
    publication.setReleaseDate(dto.getReleaseDate());
    publication.setDownloadUrl(dto.getDownloadUrl());
    publication.setType(dto.getType());
    publication.setLanguage(dto.getLanguage());

    publication = publicationRepository.save(publication);
    return publicationMapper.toResponseDTO(publication);
  }

  public void delete(Long id, User currentUser) {
    Publication publication = publicationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Publication not found with id: " + id));

    StatisticalActivity activity = publication.getActivity();

    // Ownership check
    if (!activity.getCreatedBy().getId().equals(currentUser.getId())
            && currentUser.getRole() != UserRole.ADMIN) {
      throw new ForbiddenException("Cannot delete publication from activity you don't own");
    }

    publicationRepository.delete(publication);
  }
}