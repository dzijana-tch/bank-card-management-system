package com.charniuk.bankcardmanagementsystem.service.impl;

import com.charniuk.bankcardmanagementsystem.dto.request.CardLimitRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardLimitResponse;
import com.charniuk.bankcardmanagementsystem.exceptions.CardLimitAlreadyExistsException;
import com.charniuk.bankcardmanagementsystem.mapper.CardLimitMapper;
import com.charniuk.bankcardmanagementsystem.model.Card;
import com.charniuk.bankcardmanagementsystem.model.CardLimit;
import com.charniuk.bankcardmanagementsystem.repository.CardLimitRepository;
import com.charniuk.bankcardmanagementsystem.service.CardLimitService;
import com.charniuk.bankcardmanagementsystem.service.CardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardLimitServiceImpl implements CardLimitService {

  private final CardLimitRepository cardLimitRepository;
  private final CardService cardService;
  private final CardLimitMapper cardLimitMapper;

  @Override
  @Transactional
  public CardLimitResponse setLimit(CardLimitRequest cardLimitRequest) {

    if (cardLimitRepository.existsByCard_CardIdAndType(cardLimitRequest.getCardId(),
        cardLimitRequest.getType())) {
      throw new CardLimitAlreadyExistsException(
          "Лимит типа " + cardLimitRequest.getType().name() + " для карты " + cardLimitRequest.getCardId().toString()
          + " уже существует");
    }
    Card card = cardService.getByCardId(cardLimitRequest.getCardId());
    CardLimit cardLimit = cardLimitMapper.toEntity(cardLimitRequest, card);
    cardLimitRepository.save(cardLimit);

    return cardLimitMapper.toResponse(cardLimit);
  }

  @Override
  @Transactional(readOnly = true)
  public List<CardLimit> getLimitsByCard(Card card) {
    return cardLimitRepository.findByCard(card);
  }
}
