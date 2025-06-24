package com.charniuk.bankcardmanagementsystem.dto.request;

import com.charniuk.bankcardmanagementsystem.enums.CardLimitType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание лимита по карте")
@Builder
public class CardLimitRequest {

  @Schema(description = "ID карты", example = "65165863-7d85-452d-b69c-93421fdf551e")
  @NotNull(message = "ID не может быть null")
  private UUID cardId;

  @Schema(description = "Типа лимита", example = "DAILY")
  @NotBlank(message = "Тип лимита не может быть пустым")
  @Pattern(regexp = "DAILY|MONTHLY", message = "Допустимые типы: DAILY или MONTHLY")
  private CardLimitType type;

  @Schema(description = "Размер лимита", example = "1000")
  @NotNull(message = "Сумма не может быть null")
  private BigDecimal amount;

}