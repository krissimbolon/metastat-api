package com.bps.metastat.domain.entity;

import com.bps.metastat.domain.enums.Language;
import com.bps.metastat.domain.enums.PublicationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "publications")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Publication {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 500)
  @NotBlank(message = "Publication title cannot be empty")
  @Size(max = 500, message = "Publication title cannot exceed 500 characters")
  private String title; // "Berita Resmi Statistik Susenas Maret 2024"

  @Column(length = 50)
  private String isbn; // "978-602-438-xxx-x"

  @Column(length = 50)
  private String catalogNumber; // "1101002"

  private LocalDate releaseDate;

  @Column(length = 500)
  @URL(message = "Download URL must be valid")
  @Size(max = 500, message = "Download URL cannot exceed 500 characters")
  private String downloadUrl; // URL file PDF

  @Enumerated(EnumType.STRING)
  private PublicationType type; // BRS, PUBLICATION, INFOGRAPHIC, METADATA

  @Enumerated(EnumType.STRING)
  private Language language; // ID, EN

  // Relation
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "activity_id", nullable = false)
  @NotNull(message = "Activity cannot be null")
  private StatisticalActivity activity;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;
}
