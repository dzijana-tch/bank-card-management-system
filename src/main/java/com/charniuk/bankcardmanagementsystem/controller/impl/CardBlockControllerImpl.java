package com.charniuk.bankcardmanagementsystem.controller.impl;

import com.charniuk.bankcardmanagementsystem.controller.CardBlockController;
import com.charniuk.bankcardmanagementsystem.dto.request.CardBlockRequestDto;
import com.charniuk.bankcardmanagementsystem.dto.response.CardBlockResponse;
import com.charniuk.bankcardmanagementsystem.service.CardBlockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards/block_requests")
@RequiredArgsConstructor
public class CardBlockControllerImpl implements CardBlockController {

  private final CardBlockService cardBlockService;

  @PostMapping
  @Override
  @PreAuthorize("hasRole('ADMIN') or "
      + "@cardServiceImpl.isCardOwner(#cardBlockRequestDto.cardId, authentication.principal.user.userId)")
  public ResponseEntity<CardBlockResponse> createBlockRequest(
      @RequestBody @Valid CardBlockRequestDto cardBlockRequestDto) {

    return ResponseEntity.ok(
        cardBlockService.createBlockRequest(cardBlockRequestDto));
  }
}
