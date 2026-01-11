package com.bps.metastat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
  private Boolean success;
  private String error;
  private String message;
  private List<String> details;
  private LocalDateTime timestamp;

  public static ErrorResponse of(String error, String message) {
    return ErrorResponse.builder()
        .success(false)
        .error(error)
        .message(message)
        .timestamp(LocalDateTime.now())
        .build();
  }

  public static ErrorResponse of(String error, String message, List<String> details) {
    return ErrorResponse.builder()
        .success(false)
        .error(error)
        .message(message)
        .details(details)
        .timestamp(LocalDateTime.now())
        .build();
  }
}
