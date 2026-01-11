package com.bps.metastat.service;

import com.bps.metastat.domain.entity.Organization;
import com.bps.metastat.domain.repository.OrganizationRepository;
import com.bps.metastat.dto.response.OrganizationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public List<OrganizationDTO> findAll() {
        return organizationRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private OrganizationDTO toDTO(Organization org) {
        return OrganizationDTO.builder()
                .id(org.getId())
                .name(org.getName())
                .type(org.getType())
                .build();
    }
}
