package com.charniuk.bankcardmanagementsystem.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.charniuk.bankcardmanagementsystem.dto.request.CardLimitRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardLimitResponse;
import com.charniuk.bankcardmanagementsystem.enums.CardLimitType;
import com.charniuk.bankcardmanagementsystem.exceptions.CardLimitAlreadyExistsException;
import com.charniuk.bankcardmanagementsystem.mapper.CardLimitMapper;
import com.charniuk.bankcardmanagementsystem.model.Card;
import com.charniuk.bankcardmanagementsystem.model.CardLimit;
import com.charniuk.bankcardmanagementsystem.repository.CardLimitRepository;
import com.charniuk.bankcardmanagementsystem.service.CardService;
import com.charniuk.bankcardmanagementsystem.service.impl.CardLimitServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardLimitServiceTest {

  @InjectMocks
  private CardLimitServiceImpl cardLimitService;

  @Mock
  private CardLimitRepository cardLimitRepository;

  @Mock
  private CardService cardService;

  @Mock
  private CardLimitMapper cardLimitMapper;

  @Test
  @DisplayName("Limit setting is ok")
  void setLimit_is_ok() {

    UUID cardId = UUID.randomUUID();
    CardLimitType type = CardLimitType.DAILY;

    CardLimitRequest cardLimitRequest = CardLimitRequest.builder()
        .cardId(cardId)
        .type(type)
        .amount(BigDecimal.valueOf(10000))
        .build();

    Card card = Card.builder()
        .build();

    CardLimit cardLimit = CardLimit.builder()
        .build();

    CardLimitResponse cardLimitResponse = CardLimitResponse.builder()
        .build();

    when(cardLimitRepository.existsByCard_CardIdAndType(cardId, type)).thenReturn(false);
    when(cardService.getByCardId(cardId)).thenReturn(card);
    when(cardLimitMapper.toEntity(cardLimitRequest, card)).thenReturn(cardLimit);
    when(cardLimitMapper.toResponse(cardLimit)).thenReturn(cardLimitResponse);

    assertEquals(cardLimitResponse, cardLimitService.setLimit(cardLimitRequest));

    verify(cardLimitRepository).save(cardLimit);
  }

  @Test
  @DisplayName("Limit setting already exists")
  void setLimit_already_exists() {

    UUID cardId = UUID.randomUUID();
    CardLimitType type = CardLimitType.DAILY;

    CardLimitRequest cardLimitRequest = CardLimitRequest.builder()
        .cardId(cardId)
        .type(type)
        .amount(BigDecimal.valueOf(10000))
        .build();

    when(cardLimitRepository.existsByCard_CardIdAndType(cardId, type)).thenReturn(true);

    assertThrows(CardLimitAlreadyExistsException.class,
        () -> cardLimitService.setLimit(cardLimitRequest));
  }

  @Test
  @DisplayName("Getting limits by card is ok")
  void getLimitsByCard_is_ok() {

    Card card = Card.builder().build();
    List<CardLimit> limits = List.of(CardLimit.builder().build());

    when(cardLimitRepository.findByCard(card)).thenReturn(limits);

    assertEquals(limits, cardLimitService.getLimitsByCard(card));
  }
}
