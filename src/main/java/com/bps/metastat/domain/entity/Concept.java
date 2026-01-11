package com.bps.metastat.domain.entity;

import com.bps.metastat.domain.enums.VariableDataType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Concept represents statistical concepts/terminology (SDMX/GSIM compliant).
 * In this system, concepts are derived from Variables.
 */
@Entity
@Table(name = "concepts")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Concept {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 50)
  @NotBlank(message = "Concept code cannot be empty")
  @Size(max = 50, message = "Concept code cannot exceed 50 characters")
  private String code; // Unique concept identifier

  @Column(nullable = false, length = 300)
  @NotBlank(message = "Concept name cannot be empty")
  @Size(max = 300, message = "Concept name cannot exceed 300 characters")
  private String name;

  @Column(length = 2000)
  @Size(max = 2000, message = "Definition cannot exceed 2000 characters")
  private String definition;

  @Enumerated(EnumType.STRING)
  private VariableDataType dataType;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;
}
