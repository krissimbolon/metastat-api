package com.bps.metastat.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "indicators")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Indicator {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(length = 2000)
  private String formula;

  @Column(length = 2000)
  private String publication;

  // Relations
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "activity_id", nullable = false)
  private StatisticalActivity activity;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "indicator_variable_mapping",
      joinColumns = @JoinColumn(name = "indicator_id"),
      inverseJoinColumns = @JoinColumn(name = "variable_id"))
  @Builder.Default
  private Set<Variable> variables = new HashSet<>();
}
