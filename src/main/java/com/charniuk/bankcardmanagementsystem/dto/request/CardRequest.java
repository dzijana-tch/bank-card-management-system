package com.charniuk.bankcardmanagementsystem.dto.request;

import com.charniuk.bankcardmanagementsystem.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание карты")
public class CardRequest {

  @Schema(description = "Номер карты", example = "9999999999999999")
  @Size(min = 16, max = 16, message = "Номер карты должен состоять из 16 символов")
  @NotBlank(message = "Номер карты не может быть пустым")
  private String cardNumber;

  @Schema(description = "Имя владельца карты", example = "IVAN IVANOV")
  @Size(min = 5, max = 50, message = "Имя владельца карты должно содержать от 5 до 50 символов")
  @NotBlank(message = "Имя владельца карты не может быть пустым")
  private String cardHolderName;

  @Schema(description = "Дата, до которой действительна карта", example = "2026-10-31")
  @NotNull(message = "Дата не может быть null")
  private LocalDate expirationDate;

  @Schema(description = "Статус карты", example = "ACTIVE")
  @NotBlank(message = "Статус не может быть пустым")
  @Pattern(regexp = "ACTIVE|BLOCKED|EXPIRED", message = "Допустимые статусы: ACTIVE, BLOCKED или EXPIRED")
  private CardStatus status;

  @Schema(description = "Баланс карты", example = "1000")
  @NotNull(message = "Баланс не может быть null")
  private BigDecimal balance;

  @Schema(description = "ID пользователя", example = "65165863-7d85-452d-b69c-93421fdf551e")
  @NotNull(message = "ID не может быть null")
  private UUID userId;
}