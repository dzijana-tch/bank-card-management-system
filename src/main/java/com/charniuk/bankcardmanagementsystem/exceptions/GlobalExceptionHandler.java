package com.charniuk.bankcardmanagementsystem.exceptions;

import com.charniuk.bankcardmanagementsystem.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentials(HttpServletRequest request) {
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ErrorResponse.builder()
            .uri(request.getRequestURI())
            .type("401 BAD CREDENTIALS")
            .message("Неверный email или пароль")
            .timestamp(Instant.now().getEpochSecond())
            .build());
  }

  @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleAuthenticationCredentialsNotFound(HttpServletRequest request) {
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ErrorResponse.builder()
            .uri(request.getRequestURI())
            .type("401 UNAUTHORIZED")
            .message("Требуется вход в систему")
            .timestamp(Instant.now().getEpochSecond())
            .build());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDenied(
      HttpServletRequest request) {

    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(ErrorResponse.builder()
            .uri(request.getRequestURI())
            .type("403 FORBIDDEN")
            .message("Доступ запрещён")
            .timestamp(Instant.now().getEpochSecond())
            .build());
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFound(HttpServletRequest request) {

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.builder()
            .uri(request.getRequestURI())
            .type("404 NOT FOUND")
            .message("Пользователь не найден")
            .timestamp(Instant.now().getEpochSecond())
            .build());
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExists(HttpServletRequest request) {

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(ErrorResponse.builder()
            .uri(request.getRequestURI())
            .type("409 USER ALREADY EXISTS")
            .message("Данный пользователь уже существует")
            .timestamp(Instant.now().getEpochSecond())
            .build());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntime(HttpServletRequest request) {

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.builder()
            .uri(request.getRequestURI())
            .type("500 INTERNAL SERVER ERROR")
            .message("Внутренняя ошибка сервера")
            .timestamp(Instant.now().getEpochSecond())
            .build());
  }

}
