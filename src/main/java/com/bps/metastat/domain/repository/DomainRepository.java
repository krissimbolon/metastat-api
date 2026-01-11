package com.bps.metastat.domain.repository;

import com.bps.metastat.domain.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
  Optional<Domain> findByCode(String code);
}
