package com.charniuk.bankcardmanagementsystem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ c токеном доступа")
public class JwtAuthenticationResponse {

  @Schema(description = "Токен доступа", example = "eyJhbGclOiYIUzI1NiJ9.eyJzdWIiOiI4MDEyOWU5Mi1lMjZlLTRjYTgtYT...")
  private String token;
}