package com.charniuk.bankcardmanagementsystem.service;

import com.charniuk.bankcardmanagementsystem.dto.request.CardRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardResponse;
import com.charniuk.bankcardmanagementsystem.enums.CardStatus;
import com.charniuk.bankcardmanagementsystem.model.Card;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface CardService {

  /**
   * Создание карты
   *
   * @return dto с информацией о созданной карте
   */
  CardResponse create(CardRequest cardRequest);

  /**
   * Блокирование карты
   *
   * @return dto с информацией о карте
   */
  CardResponse block(UUID cardId);

  /**
   * Получение карты из репозитория по id
   *
   * @return карта
   */
  Card getByCardId(UUID cardId);

  /**
   * Активирование карты
   *
   * @return dto с информацией о карте
   */
  CardResponse activate(UUID cardId);

  /**
   * Удаление карты
   *
   * @return dto с информацией об удалённой карте
   */
  CardResponse delete(UUID cardId);

  /**
   * Получить все карты (c пагинацией)
   *
   * @return List dto с информацией о всех картах
   */
  List<CardResponse> getAllCards(UUID userId, String cardHolderName, CardStatus status,
      Pageable pageable);

  /**
   * Проверяет, является ли пользователь владельцем карты.
   *
   * @param cardId ID карты
   * @param userId ID пользователя
   * @return true, если пользователь - владелец, иначе false
   */
  boolean isCardOwner(UUID cardId, UUID userId);

}
