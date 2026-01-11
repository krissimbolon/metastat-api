package com.bps.metastat.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "subject_categories")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 10)
  @NotBlank(message = "Code cannot be empty")
  @Size(max = 10, message = "Code cannot exceed 10 characters")
  private String code; // "SOC", "ECO", "AGR"

  @Column(nullable = false, length = 200)
  @NotBlank(message = "Name cannot be empty")
  @Size(max = 200, message = "Name cannot exceed 200 characters")
  private String name; // "Sosial", "Ekonomi", "Pertanian"

  @Column(length = 1000)
  private String description;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_category_id")
  private SubjectCategory parentCategory; // Hierarki subjek

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;
}