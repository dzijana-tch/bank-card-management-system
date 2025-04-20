package com.charniuk.bankcardmanagementsystem.controller.impl;

import com.charniuk.bankcardmanagementsystem.controller.CardController;
import com.charniuk.bankcardmanagementsystem.dto.request.CardFilterRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.CardRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardResponse;
import com.charniuk.bankcardmanagementsystem.service.CardService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardControllerImpl implements CardController {

  private final CardService cardService;

  @PostMapping
  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CardResponse> create(
      @RequestBody @Valid CardRequest cardRequest) {

    return ResponseEntity.ok(
        cardService.create(cardRequest));
  }

  @PatchMapping("/blocking/{card_id}")
  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CardResponse> block(
      @PathVariable("card_id") UUID cardId) {

    return ResponseEntity.ok(
        cardService.block(cardId));
  }

  @PatchMapping("/activation/{card_id}")
  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CardResponse> activate(
      @PathVariable("card_id") UUID cardId) {

    return ResponseEntity.ok(
        cardService.activate(cardId));
  }

  @DeleteMapping("/{card_id}")
  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<CardResponse> delete(
      @PathVariable("card_id") UUID cardId) {

    return ResponseEntity.ok(
        cardService.delete(cardId));
  }

  @GetMapping()
  @Override
  @PreAuthorize("hasRole('ADMIN') or "
      + "@cardServiceImpl.isCardOwner(#cardFilterRequest.cardId, authentication.principal.user.userId)")
  public ResponseEntity<List<CardResponse>> get(
      @RequestBody @Valid CardFilterRequest cardFilterRequest,
      @RequestParam(value = "sort_direction", defaultValue = "ASC") String sortDirection,
      @RequestParam(value = "sort_by", defaultValue = "expirationDate") String sortBy,
      @RequestParam Integer offset,
      @RequestParam Integer limit) {

    Sort sort = sortDirection.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() :
        Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(offset, limit, sort);

    return ResponseEntity.ok(
        cardService.getAllCards(cardFilterRequest, pageable));
  }
}
