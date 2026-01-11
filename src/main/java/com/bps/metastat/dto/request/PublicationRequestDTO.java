package com.bps.metastat.dto.request;

import com.bps.metastat.domain.enums.Language;
import com.bps.metastat.domain.enums.PublicationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationRequestDTO {

  @NotBlank(message = "Publication title cannot be empty")
  @Size(max = 500, message = "Publication title cannot exceed 500 characters")
  private String title;

  @Size(max = 50, message = "ISBN cannot exceed 50 characters")
  private String isbn;

  @Size(max = 50, message = "Catalog number cannot exceed 50 characters")
  private String catalogNumber;

  private LocalDate releaseDate;

  @URL(message = "Download URL must be valid")
  @Size(max = 500, message = "Download URL cannot exceed 500 characters")
  private String downloadUrl;

  private PublicationType type;
  private Language language;

  @NotNull(message = "Activity ID cannot be null")
  private Long activityId;
}
