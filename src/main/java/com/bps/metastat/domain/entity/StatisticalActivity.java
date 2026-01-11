package com.bps.metastat.domain.entity;

import com.bps.metastat.domain.enums.ActivityStatus;
import com.bps.metastat.domain.enums.CollectionTechnique;
import com.bps.metastat.domain.enums.DataCollectionMethod;
import com.bps.metastat.domain.enums.Frequency;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "statistical_activities")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticalActivity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // A. Identity
  @Column(unique = true, nullable = false, length = 50)
  private String activityCode; // Auto-generated: "ACT-2024-001"

  @Column(nullable = false, length = 500)
  @NotBlank(message = "Title cannot be empty")
  @Size(max = 500, message = "Title cannot exceed 500 characters")
  private String title; // "Susenas Maret 2024"

  @Column(nullable = false)
  @NotNull(message = "Year cannot be null")
  @Min(value = 1900, message = "Year must be >= 1900")
  @Max(value = 2100, message = "Year must be <= 2100")
  private Integer year; // 2024

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private ActivityStatus status = ActivityStatus.DRAFT; // DRAFT, PUBLISHED

  // B. Description
  @Column(length = 5000)
  @Size(max = 5000, message = "Description cannot exceed 5000 characters")
  private String description; // Deskripsi kegiatan

  @Column(length = 3000)
  private String background; // Latar belakang

  @Column(length = 3000)
  private String objectives; // Tujuan kegiatan

  // C. Methodology
  @Enumerated(EnumType.STRING)
  private DataCollectionMethod collectionMethod; // CENSUS, SURVEY, COMPILATION

  @Enumerated(EnumType.STRING)
  private Frequency frequency; // ANNUAL, QUARTERLY, MONTHLY, ONE_TIME

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "activity_collection_techniques", joinColumns = @JoinColumn(name = "activity_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "technique")
  @Builder.Default
  private Set<CollectionTechnique> collectionTechniques = new HashSet<>(); // CAPI, CAWI, PAPI, CATI

  @Column(length = 200)
  private String coverageArea; // "Nasional", "34 Provinsi", "Jawa Barat"

  @Column(length = 200)
  private String referencePeriod; // "1 tahun lalu", "kondisi saat ini"

  // D. Sampling (simplified for MVP)
  @Column(length = 200)
  private String samplingMethod; // "Multistage Random Sampling"

  private Integer sampleSize; // 300000

  // E. Dissemination
  @Column(nullable = false)
  @Builder.Default
  private Boolean isPublic = true;

  private LocalDate releaseDate; // Tanggal rilis data

  // F. Relations
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "domain_id", nullable = false)
  @NotNull(message = "Domain cannot be null")
  private Domain domain; // Cakupan wilayah

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "organization_id", nullable = false)
  @NotNull(message = "Organization cannot be null")
  private Organization organization; // Penyelenggara

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "activity_subjects",
      joinColumns = @JoinColumn(name = "activity_id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id"))
  @NotEmpty(message = "At least one subject is required")
  @Builder.Default
  private Set<SubjectCategory> subjects = new HashSet<>();

  // G. Child Collections
  @JsonIgnore
  @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @Builder.Default
  private List<Variable> variables = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @Builder.Default
  private List<Publication> publications = new ArrayList<>();

  // H. Audit
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "created_by", nullable = false)
  @NotNull(message = "CreatedBy cannot be null")
  private User createdBy; // User yang membuat

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime updatedAt;
}
