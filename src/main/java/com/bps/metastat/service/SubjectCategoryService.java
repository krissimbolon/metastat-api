package com.bps.metastat.service;

import com.bps.metastat.domain.entity.SubjectCategory;
import com.bps.metastat.domain.repository.SubjectCategoryRepository;
import com.bps.metastat.dto.response.SubjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectCategoryService {

    private final SubjectCategoryRepository subjectCategoryRepository;

    public List<SubjectDTO> findAll() {
        return subjectCategoryRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private SubjectDTO toDTO(SubjectCategory subject) {
        return SubjectDTO.builder()
                .id(subject.getId())
                .code(subject.getCode())
                .name(subject.getName())
                .build();
    }
}
