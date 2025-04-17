package com.charniuk.bankcardmanagementsystem.dto.request;

import com.charniuk.bankcardmanagementsystem.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;

@Data
@Schema(description = "Информация о фильтрации транзакций")
public class TransactionFilterRequest {

  @Schema(description = "Типа транзакции", example = "WITHDRAWAL")
  private TransactionType type;

  @Schema(description = "ID карты", example = "65165863-7d85-452d-b69c-93421fdf551e")
  private UUID cardId;
}
