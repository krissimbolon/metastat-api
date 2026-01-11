package com.bps.metastat.domain.repository;

import com.bps.metastat.domain.entity.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConceptRepository extends JpaRepository<Concept, Long> {
  Optional<Concept> findByCode(String code);

  @Query("SELECT c FROM Concept c WHERE " +
      "LOWER(c.name) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
      "LOWER(c.definition) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
      "LOWER(c.code) LIKE LOWER(CONCAT('%', :term, '%'))")
  List<Concept> searchByTerm(@Param("term") String term);

  @Query("SELECT c FROM Concept c WHERE c.code = :code")
  Optional<Concept> findByCodeExact(@Param("code") String code);
}
