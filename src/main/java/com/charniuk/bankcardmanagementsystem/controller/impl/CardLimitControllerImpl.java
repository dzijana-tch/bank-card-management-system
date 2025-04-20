package com.charniuk.bankcardmanagementsystem.controller.impl;

import com.charniuk.bankcardmanagementsystem.controller.CardLimitController;
import com.charniuk.bankcardmanagementsystem.dto.request.CardLimitRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardLimitResponse;
import com.charniuk.bankcardmanagementsystem.service.CardLimitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards/limits")
@RequiredArgsConstructor
public class CardLimitControllerImpl implements CardLimitController {

  private final CardLimitService cardLimitService;

  @PostMapping
  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CardLimitResponse> setLimit(
      @RequestBody @Valid CardLimitRequest cardLimitRequest) {

    return ResponseEntity.ok(
        cardLimitService.setLimit(cardLimitRequest));
  }
}
