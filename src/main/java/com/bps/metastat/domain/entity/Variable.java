package com.bps.metastat.domain.entity;

import com.bps.metastat.domain.enums.VariableDataType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "variables")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Variable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 300)
  @NotBlank(message = "Variable name cannot be empty")
  @Size(max = 300, message = "Variable name cannot exceed 300 characters")
  private String name; // "Pengeluaran per kapita sebulan"

  @Column(length = 50)
  @Size(max = 50, message = "Alias cannot exceed 50 characters")
  private String alias; // "r301" (kode kuesioner)

  @Column(length = 2000)
  @Size(max = 2000, message = "Definition cannot exceed 2000 characters")
  private String definition; // Definisi operasional

  @Column(length = 5000)
  @Size(max = 5000, message = "Question text cannot exceed 5000 characters")
  private String questionText; // Teks pertanyaan di kuesioner

  @Enumerated(EnumType.STRING)
  private VariableDataType dataType; // NUMERIC, CATEGORICAL, TEXT, DATE, BOOLEAN

  @Column(length = 200)
  private String referencePeriod; // "seminggu terakhir", "1 tahun lalu"

  // Relation
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "activity_id", nullable = false)
  @NotNull(message = "Activity cannot be null")
  private StatisticalActivity activity;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;
}
