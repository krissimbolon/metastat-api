package com.bps.metastat.domain.repository;

import com.bps.metastat.domain.entity.StatisticalActivity;
import com.bps.metastat.domain.entity.Variable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariableRepository extends JpaRepository<Variable, Long> {
  Page<Variable> findByActivity(StatisticalActivity activity, Pageable pageable);

  List<Variable> findByActivity(StatisticalActivity activity);
}
