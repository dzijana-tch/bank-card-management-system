package com.charniuk.bankcardmanagementsystem.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.charniuk.bankcardmanagementsystem.dto.request.CardRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardResponse;
import com.charniuk.bankcardmanagementsystem.dto.response.UserResponse;
import com.charniuk.bankcardmanagementsystem.enums.CardStatus;
import com.charniuk.bankcardmanagementsystem.exceptions.CardNotFoundException;
import com.charniuk.bankcardmanagementsystem.exceptions.UserNotFoundException;
import com.charniuk.bankcardmanagementsystem.mapper.CardMapper;
import com.charniuk.bankcardmanagementsystem.model.Card;
import com.charniuk.bankcardmanagementsystem.model.User;
import com.charniuk.bankcardmanagementsystem.repository.CardRepository;
import com.charniuk.bankcardmanagementsystem.service.UserService;
import com.charniuk.bankcardmanagementsystem.service.impl.CardServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

  @InjectMocks
  private CardServiceImpl cardService;

  @Mock
  private UserService userService;

  @Mock
  private CardMapper cardMapper;

  @Mock
  private CardRepository cardRepository;

  @Test
  @DisplayName("Create card is ok")
  void create_is_ok() {

    UUID userId = UUID.randomUUID();
    User user = User.builder()
        .userId(userId)
        .build();

    CardRequest cardRequest = CardRequest.builder()
        .userId(userId)
        .build();

    Card card = Card.builder()
        .user(user)
        .build();

    CardResponse cardResponse = CardResponse.builder()
        .userResponse(UserResponse.builder().userId(userId).build())
        .build();

    when(userService.getByUserId(userId)).thenReturn(user);
    when(cardMapper.toEntity(cardRequest, user)).thenReturn(card);
    when(cardMapper.toResponse(card)).thenReturn(cardResponse);

    assertEquals(cardResponse, cardService.create(cardRequest));
    verify(cardRepository, times(1)).save(card);
  }


  @Test
  @DisplayName("Create card when user is not found")
  void create_not_found() {

    UUID userId = UUID.randomUUID();

    CardRequest cardRequest = CardRequest.builder()
        .userId(userId)
        .build();

    when(userService.getByUserId(userId)).thenThrow(UserNotFoundException.class);

    assertThrows(UserNotFoundException.class, () -> cardService.create(cardRequest));
  }

  @Test
  @DisplayName("Block card is ok")
  void block_is_ok() {

    UUID cardId = UUID.randomUUID();

    Card card = Card.builder()
        .cardId(cardId)
        .status(CardStatus.ACTIVE)
        .build();

    CardResponse cardResponse = CardResponse.builder()
        .cardId(cardId)
        .status(CardStatus.BLOCKED)
        .build();

    when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
    when(cardMapper.toResponse(card)).thenReturn(cardResponse);

    assertEquals(cardResponse, cardService.block(cardId));

    ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);
    verify(cardMapper).toResponse(cardCaptor.capture());
    Card changedCard = cardCaptor.getValue();
    assertEquals(CardStatus.BLOCKED, changedCard.getStatus());
  }

  @Test
  @DisplayName("Get by card id is ok")
  void getByCardId_is_ok() {

    UUID cardId = UUID.randomUUID();

    Card card = Card.builder()
        .cardId(cardId)
        .build();

    when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

    assertEquals(card, cardService.getByCardId(cardId));
  }

  @Test
  @DisplayName("Get by card id is ok")
  void getByCardId_not_found() {

    UUID cardId = UUID.randomUUID();

    when(cardRepository.findById(cardId)).thenThrow(CardNotFoundException.class);

    assertThrows(CardNotFoundException.class, () -> cardService.getByCardId(cardId));
  }

  @Test
  @DisplayName("Activate card is ok")
  void activate_is_ok() {

    UUID cardId = UUID.randomUUID();

    Card card = Card.builder()
        .cardId(cardId)
        .status(CardStatus.BLOCKED)
        .build();

    CardResponse cardResponse = CardResponse.builder()
        .cardId(cardId)
        .status(CardStatus.ACTIVE)
        .build();

    when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
    when(cardMapper.toResponse(card)).thenReturn(cardResponse);

    assertEquals(cardResponse, cardService.activate(cardId));

    ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);
    verify(cardMapper).toResponse(cardCaptor.capture());
    Card changedCard = cardCaptor.getValue();
    assertEquals(CardStatus.ACTIVE, changedCard.getStatus());
  }

  @Test
  @DisplayName("Delete card is ok")
  void delete_is_ok() {

    UUID cardId = UUID.randomUUID();

    Card card = Card.builder()
        .cardId(cardId)
        .status(CardStatus.BLOCKED)
        .build();

    CardResponse cardResponse = CardResponse.builder()
        .cardId(cardId)
        .status(CardStatus.ACTIVE)
        .build();

    when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
    when(cardMapper.toResponse(card)).thenReturn(cardResponse);

    assertEquals(cardResponse, cardService.delete(cardId));
    verify(cardRepository, times(1)).delete(card);
  }

  @Test
  @DisplayName("Get all cards is ok")
  void getAllCards_is_ok() {

    UUID userId = UUID.randomUUID();
    String cardHolderName = "IVAN IVANOV";
    CardStatus status = CardStatus.ACTIVE;
    Pageable pageable = PageRequest.of(
        0, 10, Sort.by("expirationDate").ascending());

    List<Card> cards = List.of(Card.builder().build());
    List<CardResponse> cardResponses = List.of(CardResponse.builder().build());

    when(cardRepository.findFilteredCards(userId, cardHolderName, status, pageable))
        .thenReturn(cards);
    when(cardMapper.toResponse(cards)).thenReturn(cardResponses);

    assertEquals(cardResponses, cardService.getAllCards(userId, cardHolderName, status, pageable));
  }

  @Test
  @DisplayName("Card owner checking returns true")
  void isCardOwner_true() {

    UUID cardId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    User user = User.builder()
        .userId(userId)
        .build();

    Card card = Card.builder()
        .cardId(cardId)
        .user(user)
        .build();

    when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

    assertTrue(cardService.isCardOwner(cardId, userId));
  }

  @Test
  @DisplayName("Card owner checking returns false")
  void isCardOwner_false() {

    UUID cardId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    User user = User.builder()
        .userId(UUID.randomUUID())
        .build();

    Card card = Card.builder()
        .cardId(cardId)
        .user(user)
        .build();

    when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

    assertFalse(cardService.isCardOwner(cardId, userId));
  }
}
