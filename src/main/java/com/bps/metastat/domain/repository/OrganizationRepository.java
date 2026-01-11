package com.bps.metastat.domain.repository;

import com.bps.metastat.domain.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
  Optional<Organization> findByName(String name);
}
