package com.bps.metastat.service;

import com.bps.metastat.domain.entity.Concept;
import com.bps.metastat.domain.repository.ConceptRepository;
import com.bps.metastat.dto.response.ConceptResponseDTO;
import com.bps.metastat.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConceptService {

  private final ConceptRepository conceptRepository;

  @Transactional(readOnly = true)
  public List<ConceptResponseDTO> findAll(String term, String code) {
    List<Concept> concepts;

    if (code != null && !code.trim().isEmpty()) {
      // Exact match by code
      concepts = conceptRepository.findByCodeExact(code)
          .map(List::of)
          .orElse(List.of());
    } else if (term != null && !term.trim().isEmpty()) {
      // Search by term (name, definition, code)
      concepts = conceptRepository.searchByTerm(term);
    } else {
      // Return all
      concepts = conceptRepository.findAll();
    }

    return concepts.stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public ConceptResponseDTO findById(Long id) {
    Concept concept = conceptRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Concept not found with id: " + id));

    return toDTO(concept);
  }

  private ConceptResponseDTO toDTO(Concept concept) {
    // Count variables that use similar name/definition (related variables)
    // In a real implementation, this would query by concept-variable relationship
    // For now, we'll return 0 as a placeholder - can be enhanced with proper relationship
    int relatedVariablesCount = 0;

    return ConceptResponseDTO.builder()
        .id(concept.getId())
        .code(concept.getCode())
        .name(concept.getName())
        .definition(concept.getDefinition())
        .dataType(concept.getDataType())
        .relatedVariablesCount(relatedVariablesCount)
        .build();
  }
}
