package com.charniuk.bankcardmanagementsystem.controller;

import com.charniuk.bankcardmanagementsystem.dto.request.CardRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardResponse;
import com.charniuk.bankcardmanagementsystem.dto.response.ErrorResponse;
import com.charniuk.bankcardmanagementsystem.enums.CardStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Карты")
public interface CardController {

  /**
   * Создание карты
   *
   * @param cardRequest данные для создания карты
   * @return карта
   */
  @Operation(summary = "Создание карты",
      description = "Доступно только администратору")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CardResponse.class))),
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
  @PostMapping
  ResponseEntity<CardResponse> create(CardRequest cardRequest);

  /**
   * Блокирование карты
   *
   * @param cardId id карты
   * @return карта
   */
  @Operation(summary = "Блокирование карты",
      description = "Доступно только администратору")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CardResponse.class))),
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
  @PatchMapping("/blocking/{card_id}")
  ResponseEntity<CardResponse> block(UUID cardId);

  /**
   * Активация карты
   *
   * @param cardId id карты
   * @return карта
   */
  @Operation(summary = "Активация карты",
      description = "Доступно только администратору")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CardResponse.class))),
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
  @PatchMapping("/activation/{card_id}")
  ResponseEntity<CardResponse> activate(UUID cardId);

  /**
   * Удаление карты
   *
   * @param cardId id карты
   * @return карта
   */
  @Operation(summary = "Удаление карты",
      description = "Доступно только администратору")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CardResponse.class))),
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
  @DeleteMapping("/{card_id}")
  ResponseEntity<CardResponse> delete(UUID cardId);

  /**
   * Получить карты (c пагинацией)
   *
   * @param userId         id пользователя (опционально)
   * @param cardHolderName имя владельца карты (опционально)
   * @param status         статус карты (опционально)
   * @param sortDirection  направление сортировки (ASC, DESC)
   * @param sortBy         поле, по которому происходит сортировка
   * @param offset         номер страницы
   * @param limit          размер страницы
   * @return список карт
   */
  @Operation(summary = "Получить все карты (c пагинацией)",
      description = "Id пользователя, имя владельца и статус - опционально. "
          + "Метод доступен администратору, а также указанному пользователю")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CardResponse.class))),
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
  @GetMapping()
  ResponseEntity<List<CardResponse>> get(UUID userId, String cardHolderName, CardStatus status,
      String sortDirection, String sortBy, Integer offset, Integer limit);
}

