package com.bps.metastat.domain.entity;

import com.bps.metastat.domain.enums.OrganizationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "organizations")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organization {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 300)
  @NotBlank(message = "Name cannot be empty")
  @Size(max = 300, message = "Name cannot exceed 300 characters")
  private String name; // "BPS RI", "BPS Provinsi Jawa Barat"

  @Column(unique = true, length = 100)
  @Email(message = "Email must be valid")
  @Size(max = 100, message = "Email cannot exceed 100 characters")
  private String email; // bps@bps.go.id

  @Column(length = 500)
  private String address;

  @Column(length = 20)
  private String phone;

  @Column(length = 200)
  private String website;

  @Enumerated(EnumType.STRING)
  private OrganizationType type; // CENTRAL_NSO, PROVINCIAL_NSO, SECTORAL, INTERNATIONAL

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_organization_id")
  private Organization parentOrganization;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;
}
