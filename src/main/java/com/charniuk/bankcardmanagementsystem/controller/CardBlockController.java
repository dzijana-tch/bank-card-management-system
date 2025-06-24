package com.charniuk.bankcardmanagementsystem.controller;

import com.charniuk.bankcardmanagementsystem.dto.request.CardBlockRequestDto;
import com.charniuk.bankcardmanagementsystem.dto.response.CardBlockResponse;
import com.charniuk.bankcardmanagementsystem.dto.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Запросы на блокировку карты")
public interface CardBlockController {

  /**
   * Создание запроса на блокировку карты
   *
   * @param cardBlockRequestDto dto с информацией о блокировке
   * @return информация о созданном запросе
   */
  @Operation(summary = "Создание запроса на блокировку карты",
      description = "Доступно только владельцу карты")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CardBlockResponse.class))),
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
  ResponseEntity<CardBlockResponse> createBlockRequest(CardBlockRequestDto cardBlockRequestDto);

}
