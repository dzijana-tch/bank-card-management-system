package com.charniuk.bankcardmanagementsystem.controller;

import com.charniuk.bankcardmanagementsystem.dto.request.TransferRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.WithdrawalRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.ErrorResponse;
import com.charniuk.bankcardmanagementsystem.dto.response.TransactionResponse;
import com.charniuk.bankcardmanagementsystem.enums.TransactionType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Транзакции")
public interface TransactionController {

  /**
   * Получить транзакции (с пагинацией)
   *
   * @param type                     тип транзакции (опционально)
   * @param cardId                   id карты (опционально)
   * @param sortDirection            направление сортировки (ASC, DESC)
   * @param sortBy                   поле, по которому происходит сортировка
   * @param offset                   номер страницы
   * @param limit                    размер страницы
   * @return список транзакций
   */
  @Operation(summary = "Получить транзакции (с пагинацией)")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = TransactionResponse.class))),
          @ApiResponse(
              responseCode = "403",
              description = "Forbidden",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(
              responseCode = "404",
              description = "Not found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)))
      }
  )
  @GetMapping
  ResponseEntity<List<TransactionResponse>> get(TransactionType type, UUID cardId,
      String sortDirection, String sortBy, Integer offset, Integer limit);

  /**
   * Совершить перевод между картами
   *
   * @param transferRequest dto с инфрмацией о картах отправителя и получателя и сумме перевода
   */
  @Operation(summary = "Совершить перевод между картами")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK"),
          @ApiResponse(
              responseCode = "400",
              description = "Bad request",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(
              responseCode = "403",
              description = "Forbidden",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(
              responseCode = "404",
              description = "Not found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)))
      }
  )
  @PostMapping("/transfer")
  ResponseEntity<Void> makeTransfer(TransferRequest transferRequest);

  /**
   * Списать средства с карты
   *
   * @param withdrawalRequest dto с инфрмацией о карте и сумме снятия
   */
  @Operation(summary = "Списать средства с карты")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK"),
          @ApiResponse(
              responseCode = "400",
              description = "Bad request",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(
              responseCode = "403",
              description = "Forbidden",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(
              responseCode = "404",
              description = "Not found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)))
      }
  )
  @PostMapping("/withdrawal")
  ResponseEntity<Void> withdrawFromCard(WithdrawalRequest withdrawalRequest);
}
