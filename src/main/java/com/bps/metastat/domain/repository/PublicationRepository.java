package com.bps.metastat.domain.repository;

import com.bps.metastat.domain.entity.Publication;
import com.bps.metastat.domain.entity.StatisticalActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
  List<Publication> findByActivity(StatisticalActivity activity);
}
