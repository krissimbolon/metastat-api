package com.bps.metastat.domain.repository;

import com.bps.metastat.domain.entity.StatisticalActivity;
import com.bps.metastat.domain.entity.User;
import com.bps.metastat.domain.enums.ActivityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatisticalActivityRepository
    extends JpaRepository<StatisticalActivity, Long>, JpaSpecificationExecutor<StatisticalActivity> {

  Optional<StatisticalActivity> findByActivityCode(String activityCode);

  @Query(
      "SELECT MAX(CAST(SUBSTRING(a.activityCode, 9) AS int)) "
          + "FROM StatisticalActivity a "
          + "WHERE SUBSTRING(a.activityCode, 5, 4) = :year")
  Optional<Integer> findMaxSequenceByYear(@Param("year") String year);

  // Find published activities or activities created by user
  @Query(
      "SELECT a FROM StatisticalActivity a "
          + "WHERE a.status = 'PUBLISHED' "
          + "OR (a.status = 'DRAFT' AND a.createdBy = :user)")
  Page<StatisticalActivity> findPublishedOrOwnDraft(@Param("user") User user, Pageable pageable);

  // Find published activities only
  Page<StatisticalActivity> findByStatus(ActivityStatus status, Pageable pageable);

  Page<StatisticalActivity> findByStatusAndCreatedBy(
      ActivityStatus status, User createdBy, Pageable pageable);
}
