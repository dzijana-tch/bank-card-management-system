package com.charniuk.bankcardmanagementsystem.service.impl;

import com.charniuk.bankcardmanagementsystem.dto.request.CardBlockRequestDto;
import com.charniuk.bankcardmanagementsystem.dto.response.CardBlockResponse;
import com.charniuk.bankcardmanagementsystem.mapper.CardBlockMapper;
import com.charniuk.bankcardmanagementsystem.model.Card;
import com.charniuk.bankcardmanagementsystem.model.CardBlockRequest;
import com.charniuk.bankcardmanagementsystem.repository.CardBlockRequestRepository;
import com.charniuk.bankcardmanagementsystem.service.CardBlockService;
import com.charniuk.bankcardmanagementsystem.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardBlockServiceImpl implements CardBlockService {

  private final CardBlockMapper cardBlockMapper;
  private final CardService cardService;
  private final CardBlockRequestRepository cardBlockRequestRepository;

  @Override
  @Transactional
  public CardBlockResponse createBlockRequest(CardBlockRequestDto cardBlockRequestDto) {

    Card card = cardService.getByCardId(cardBlockRequestDto.getCardId());
    CardBlockRequest cardBlockRequest = cardBlockMapper.toEntity(cardBlockRequestDto, card);

    cardBlockRequestRepository.save(cardBlockRequest);

    return cardBlockMapper.toResponse(cardBlockRequest);
  }
}
