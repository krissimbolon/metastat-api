package com.bps.metastat.service;

import com.bps.metastat.domain.entity.Domain;
import com.bps.metastat.domain.repository.DomainRepository;
import com.bps.metastat.dto.response.DomainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DomainService {

    private final DomainRepository domainRepository;

    public List<DomainDTO> findAll() {
        return domainRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private DomainDTO toDTO(Domain domain) {
        return DomainDTO.builder()
                .id(domain.getId())
                .code(domain.getCode())
                .name(domain.getName())
                .type(domain.getType())
                .build();
    }
}