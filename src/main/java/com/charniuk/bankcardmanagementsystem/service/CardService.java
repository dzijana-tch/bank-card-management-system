package com.charniuk.bankcardmanagementsystem.service;

import com.charniuk.bankcardmanagementsystem.dto.request.CardFilterRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.CardRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardResponse;
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
  List<CardResponse> getAllCards(CardFilterRequest cardFilterRequest, Pageable pageable);

}
