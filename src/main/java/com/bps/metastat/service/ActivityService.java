package com.bps.metastat.service;

import com.bps.metastat.domain.entity.*;
import com.bps.metastat.domain.enums.ActivityStatus;
import com.bps.metastat.domain.enums.UserRole;
import com.bps.metastat.domain.repository.*;
import com.bps.metastat.dto.mapper.ActivityMapper;
import com.bps.metastat.dto.request.ActivityRequestDTO;
import com.bps.metastat.dto.response.ActivityResponseDTO;
import com.bps.metastat.dto.response.PagedResponse;
import com.bps.metastat.exception.EntityNotFoundException;
import com.bps.metastat.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ActivityService {

  private final StatisticalActivityRepository activityRepository;
  private final DomainRepository domainRepository;
  private final OrganizationRepository organizationRepository;
  private final SubjectCategoryRepository subjectCategoryRepository;
  private final ActivityMapper activityMapper;

  public PagedResponse<ActivityResponseDTO> findAll(
      Long domainId,
      Long subjectId,
      ActivityStatus status,
      Integer year,
      String search,
      Pageable pageable,
      User currentUser) {

    Specification<StatisticalActivity> spec = buildSpecification(domainId, subjectId, status, year, search, currentUser);
    Page<StatisticalActivity> activities = activityRepository.findAll(spec, pageable);
    List<ActivityResponseDTO> dtos = activities.getContent().stream()
        .map(activityMapper::toResponseDTO)
        .toList();

    return PagedResponse.<ActivityResponseDTO>builder()
        .items(dtos)
        .page(activities.getNumber())
        .size(activities.getSize())
        .total(activities.getTotalElements())
        .totalPages(activities.getTotalPages())
        .build();
  }

  private Specification<StatisticalActivity> buildSpecification(
      Long domainId, Long subjectId, ActivityStatus status, Integer year, String search, User currentUser) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Security: Filter DRAFT activities
      if (currentUser == null) {
        // Anonymous: only PUBLISHED
        predicates.add(cb.equal(root.get("status"), ActivityStatus.PUBLISHED));
      } else if (currentUser.getRole() != UserRole.ADMIN) {
        // USER: PUBLISHED or own DRAFT
        Predicate published = cb.equal(root.get("status"), ActivityStatus.PUBLISHED);
        Predicate ownDraft = cb.and(
            cb.equal(root.get("status"), ActivityStatus.DRAFT),
            cb.equal(root.get("createdBy").get("id"), currentUser.getId())
        );
        predicates.add(cb.or(published, ownDraft));
      }
      // ADMIN: no status filter (see all)

      // Filters
      if (domainId != null) {
        predicates.add(cb.equal(root.get("domain").get("id"), domainId));
      }
      if (subjectId != null) {
        predicates.add(cb.equal(root.join("subjects").get("id"), subjectId));
      }
      if (status != null && (currentUser == null || currentUser.getRole() != UserRole.ADMIN)) {
        // Only apply status filter if not already filtered by security
        // For ADMIN, allow status filter
        if (currentUser != null && currentUser.getRole() == UserRole.ADMIN) {
          predicates.add(cb.equal(root.get("status"), status));
        }
      }
      if (year != null) {
        predicates.add(cb.equal(root.get("year"), year));
      }
      if (search != null && !search.trim().isEmpty()) {
        String searchPattern = "%" + search.toLowerCase() + "%";
        predicates.add(cb.or(
            cb.like(cb.lower(root.get("title")), searchPattern),
            cb.like(cb.lower(root.get("description")), searchPattern)
        ));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }

  @Transactional(readOnly = true)
  public ActivityResponseDTO findById(Long id, User currentUser) {
    StatisticalActivity activity = activityRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + id));

    // Security check
    if (activity.getStatus() == ActivityStatus.DRAFT) {
      if (currentUser == null) {
        throw new ForbiddenException("Cannot view DRAFT activity without authentication");
      }
      if (currentUser.getRole() != UserRole.ADMIN
          && !activity.getCreatedBy().getId().equals(currentUser.getId())) {
        throw new ForbiddenException("Cannot view DRAFT activity (not owner)");
      }
    }

    return activityMapper.toResponseDTO(activity);
  }

  public ActivityResponseDTO create(ActivityRequestDTO dto, User currentUser) {
    // Validate references
    Domain domain = domainRepository.findById(dto.getDomainId())
        .orElseThrow(() -> new EntityNotFoundException("Domain not found with id: " + dto.getDomainId()));

    Organization organization = organizationRepository.findById(dto.getOrganizationId())
        .orElseThrow(() -> new EntityNotFoundException("Organization not found with id: " + dto.getOrganizationId()));

    Set<SubjectCategory> subjects = new HashSet<>();
    for (Long subjectId : dto.getSubjectIds()) {
      SubjectCategory subject = subjectCategoryRepository.findById(subjectId)
          .orElseThrow(() -> new EntityNotFoundException("Subject not found with id: " + subjectId));
      subjects.add(subject);
    }

    // Generate activityCode
    String activityCode = generateActivityCode(dto.getYear());

    // Create entity
    StatisticalActivity activity = activityMapper.toEntity(dto);
    activity.setActivityCode(activityCode);
    activity.setStatus(ActivityStatus.DRAFT);
    activity.setCreatedBy(currentUser);
    activity.setDomain(domain);
    activity.setOrganization(organization);
    activity.setSubjects(subjects);

    activity = activityRepository.save(activity);
    return activityMapper.toResponseDTO(activity);
  }

  public ActivityResponseDTO update(Long id, ActivityRequestDTO dto, User currentUser) {
    StatisticalActivity activity = activityRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + id));

    // Ownership check
    if (!activity.getCreatedBy().getId().equals(currentUser.getId())
        && currentUser.getRole() != UserRole.ADMIN) {
      throw new ForbiddenException("Cannot update activity (not owner or admin)");
    }

    // USER cannot edit PUBLISHED
    if (activity.getStatus() == ActivityStatus.PUBLISHED && currentUser.getRole() == UserRole.USER) {
      throw new ForbiddenException("Cannot edit PUBLISHED activity (USER role)");
    }

    // Update references if changed
    if (!dto.getDomainId().equals(activity.getDomain().getId())) {
      Domain domain = domainRepository.findById(dto.getDomainId())
          .orElseThrow(() -> new EntityNotFoundException("Domain not found with id: " + dto.getDomainId()));
      activity.setDomain(domain);
    }

    if (!dto.getOrganizationId().equals(activity.getOrganization().getId())) {
      Organization organization = organizationRepository.findById(dto.getOrganizationId())
          .orElseThrow(() -> new EntityNotFoundException("Organization not found with id: " + dto.getOrganizationId()));
      activity.setOrganization(organization);
    }

    // Update subjects
    Set<SubjectCategory> subjects = new HashSet<>();
    for (Long subjectId : dto.getSubjectIds()) {
      SubjectCategory subject = subjectCategoryRepository.findById(subjectId)
          .orElseThrow(() -> new EntityNotFoundException("Subject not found with id: " + subjectId));
      subjects.add(subject);
    }
    activity.setSubjects(subjects);

    // Update other fields
    activityMapper.updateEntityFromDTO(dto, activity);
    activity = activityRepository.save(activity);

    return activityMapper.toResponseDTO(activity);
  }

  public void delete(Long id, User currentUser) {
    StatisticalActivity activity = activityRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + id));

    // Ownership check
    if (!activity.getCreatedBy().getId().equals(currentUser.getId())
        && currentUser.getRole() != UserRole.ADMIN) {
      throw new ForbiddenException("Cannot delete activity (not owner or admin)");
    }

    // Cascade delete handled by JPA (orphanRemoval = true)
    activityRepository.delete(activity);
  }

  public ActivityResponseDTO changeStatus(Long id, ActivityStatus newStatus, User currentUser) {
    StatisticalActivity activity = activityRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + id));

    // Only ADMIN can PUBLISH
    if (newStatus == ActivityStatus.PUBLISHED && currentUser.getRole() != UserRole.ADMIN) {
      throw new ForbiddenException("Only ADMIN can publish activities");
    }

    activity.setStatus(newStatus);
    activity = activityRepository.save(activity);
    return activityMapper.toResponseDTO(activity);
  }

  private String generateActivityCode(Integer year) {
    String yearStr = String.valueOf(year);
    Optional<Integer> maxSequence = activityRepository.findMaxSequenceByYear(yearStr);
    int nextSequence = maxSequence.orElse(0) + 1;
    return String.format("ACT-%s-%03d", yearStr, nextSequence);
  }
}
