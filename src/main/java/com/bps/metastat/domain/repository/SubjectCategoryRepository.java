package com.bps.metastat.domain.repository;

import com.bps.metastat.domain.entity.SubjectCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectCategoryRepository extends JpaRepository<SubjectCategory, Long> {
  Optional<SubjectCategory> findByCode(String code);
}
