package com.charniuk.bankcardmanagementsystem.controller;


import com.charniuk.bankcardmanagementsystem.dto.request.SignInRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.SignUpRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.ErrorResponse;
import com.charniuk.bankcardmanagementsystem.dto.response.JwtAuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Аутентификация")
public interface AuthController {

  /**
   * Регистрация пользователя
   *
   * @param request данные пользователя
   * @return токен
   */
  @Operation(summary = "Регистрация пользователя")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = JwtAuthenticationResponse.class))),
          @ApiResponse(
              responseCode = "403",
              description = "Forbidden",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(
              responseCode = "409",
              description = "Conflict",
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
  ResponseEntity<JwtAuthenticationResponse> signUp(SignUpRequest request);

  /**
   * Авторизация пользователя
   *
   * @param request данные пользователя
   * @return токен
   */
  @Operation(summary = "Авторизация пользователя")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = JwtAuthenticationResponse.class))),
          @ApiResponse(
              responseCode = "401",
              description = "Bad credentials",
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
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)))
      }
  )
  @PostMapping
  ResponseEntity<JwtAuthenticationResponse> signIn(SignInRequest request);
}