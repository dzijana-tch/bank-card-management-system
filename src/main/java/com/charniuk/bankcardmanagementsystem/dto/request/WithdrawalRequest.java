package com.charniuk.bankcardmanagementsystem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "Запрос на списание средтв с карты")
@Builder
public class WithdrawalRequest {

  @Schema(description = "ID карты", example = "65165863-7d85-452d-b69c-93421fdf551e")
  @NotNull(message = "ID не может быть null")
  private UUID cardId;

  @Schema(description = "Сумма перевода", example = "1000")
  @NotNull(message = "Сумма не может быть null")
  private BigDecimal amount;
}
