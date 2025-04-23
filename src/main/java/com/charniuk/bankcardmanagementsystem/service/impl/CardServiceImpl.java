package com.charniuk.bankcardmanagementsystem.service.impl;

import com.charniuk.bankcardmanagementsystem.dto.request.CardRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardResponse;
import com.charniuk.bankcardmanagementsystem.enums.CardStatus;
import com.charniuk.bankcardmanagementsystem.exceptions.CardNotFoundException;
import com.charniuk.bankcardmanagementsystem.mapper.CardMapper;
import com.charniuk.bankcardmanagementsystem.model.Card;
import com.charniuk.bankcardmanagementsystem.model.User;
import com.charniuk.bankcardmanagementsystem.repository.CardRepository;
import com.charniuk.bankcardmanagementsystem.service.CardService;
import com.charniuk.bankcardmanagementsystem.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

  private final CardRepository cardRepository;
  private final UserService userService;
  private final CardMapper cardMapper;

  @Override
  @Transactional
  public CardResponse create(CardRequest cardRequest) {

    User user = userService.getByUserId(cardRequest.getUserId());
    Card card = cardMapper.toEntity(cardRequest, user);
    cardRepository.save(card);

    return cardMapper.toResponse(card);
  }

  @Override
  @Transactional
  public CardResponse block(UUID cardId) {

    Card card = getByCardId(cardId);
    card.setStatus(CardStatus.BLOCKED);

    return cardMapper.toResponse(card);
  }

  @Override
  @Transactional(readOnly = true)
  public Card getByCardId(UUID cardId) {
    return cardRepository.findById(cardId)
        .orElseThrow(() -> new CardNotFoundException("Карта с id " + cardId + " не найдена"));
  }

  @Override
  @Transactional
  public CardResponse activate(UUID cardId) {

    Card card = getByCardId(cardId);
    card.setStatus(CardStatus.ACTIVE);

    return cardMapper.toResponse(card);
  }

  @Override
  @Transactional
  public CardResponse delete(UUID cardId) {

    Card card = getByCardId(cardId);
    cardRepository.delete(card);

    return cardMapper.toResponse(card);
  }

  @Override
  public List<CardResponse> getAllCards(UUID userId, String cardHolderName, CardStatus status, Pageable pageable) {

    List<Card> cards = cardRepository.findFilteredCards(userId, cardHolderName, status, pageable);
    return cardMapper.toResponse(cards);
  }

  @Override
  @Transactional
  public boolean isCardOwner(UUID cardId, UUID userId) {

    Card card = getByCardId(cardId);
    return card.getUser().getUserId().equals(userId);
  }


}
