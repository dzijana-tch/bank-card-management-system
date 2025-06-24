package com.charniuk.bankcardmanagementsystem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "ДТО для запроса на блокировку карты")
@Builder
public class CardBlockRequestDto {

  @Schema(description = "ID карты", example = "65165863-7d85-452d-b69c-93421fdf551e")
  @NotNull(message = "ID не может быть null")
  private UUID cardId;

  @Schema(description = "Причина блокировки", example = "Кража карты")
  @Size(max = 255, message = "Причина должна содержать до 255 символов")
  @NotBlank(message = "Причина не должна быть пустой")
  private String reason;
}
