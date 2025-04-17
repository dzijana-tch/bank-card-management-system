package com.charniuk.bankcardmanagementsystem.dto.request;

import com.charniuk.bankcardmanagementsystem.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Data;

@Data
@Schema(description = "Информация о фильтрации списка карт")
public class CardFilterRequest {

  @Schema(description = "ID карты", example = "65165863-7d85-452d-b69c-93421fdf551e")
  private UUID cardId;

  @Schema(description = "Имя владельца карты", example = "IVAN IVANOV")
  @Size(min = 5, max = 50, message = "Имя владельца карты должно содержать от 5 до 50 символов")
  private String cardHolderName;

  @Schema(description = "Статус карты", example = "ACTIVE")
  @Pattern(regexp = "ACTIVE|BLOCKED|EXPIRED", message = "Допустимые статусы: ACTIVE, BLOCKED или EXPIRED")
  private CardStatus status;

}
