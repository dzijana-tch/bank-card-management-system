package com.charniuk.bankcardmanagementsystem.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.charniuk.bankcardmanagementsystem.dto.request.CardBlockRequestDto;
import com.charniuk.bankcardmanagementsystem.dto.response.CardBlockResponse;
import com.charniuk.bankcardmanagementsystem.mapper.CardBlockMapper;
import com.charniuk.bankcardmanagementsystem.model.Card;
import com.charniuk.bankcardmanagementsystem.model.CardBlockRequest;
import com.charniuk.bankcardmanagementsystem.repository.CardBlockRequestRepository;
import com.charniuk.bankcardmanagementsystem.service.CardService;
import com.charniuk.bankcardmanagementsystem.service.impl.CardBlockServiceImpl;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardBlockServiceTest {

  @InjectMocks
  private CardBlockServiceImpl cardBlockService;

  @Mock
  private CardBlockMapper cardBlockMapper;

  @Mock
  private CardService cardService;

  @Mock
  private CardBlockRequestRepository cardBlockRequestRepository;

  @Test
  @DisplayName("Create block request is ok")
  void createBlockRequest_is_ok() {

    UUID cardId = UUID.randomUUID();
    CardBlockRequestDto cardBlockRequestDto = CardBlockRequestDto.builder()
        .cardId(cardId)
        .build();

    Card card = Card.builder()
        .cardId(cardId)
        .build();

    CardBlockRequest cardBlockRequest = CardBlockRequest.builder()
        .card(card)
        .build();

    CardBlockResponse cardBlockResponse = CardBlockResponse.builder()
        .cardId(cardId)
        .build();

    when(cardService.getByCardId(cardId)).thenReturn(card);
    when(cardBlockMapper.toEntity(cardBlockRequestDto, card)).thenReturn(cardBlockRequest);
    when(cardBlockMapper.toResponse(cardBlockRequest)).thenReturn(cardBlockResponse);

    assertEquals(cardBlockResponse, cardBlockService.createBlockRequest(cardBlockRequestDto));
    verify(cardBlockRequestRepository, times(1)).save(cardBlockRequest);
  }
}
