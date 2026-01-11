package com.bps.metastat.domain.entity;

import com.bps.metastat.domain.enums.DomainType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "domains")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Domain {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 10)
  @NotBlank(message = "Code cannot be empty")
  @Size(min = 4, max = 10, message = "Code must be between 4 and 10 characters")
  private String code; // "0000", "3200", "3273"

  @Column(nullable = false, length = 200)
  @NotBlank(message = "Name cannot be empty")
  @Size(max = 200, message = "Name cannot exceed 200 characters")
  private String name; // "Indonesia", "Jawa Barat", "Kota Bandung"

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @NotNull(message = "Type cannot be null")
  private DomainType type; // NATIONAL, PROVINCIAL, DISTRICT

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_domain_id")
  private Domain parentDomain; // Hierarki: Nasional > Provinsi > Kab/Kota

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime updatedAt;
}