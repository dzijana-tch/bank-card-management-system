package com.charniuk.bankcardmanagementsystem.service;

import com.charniuk.bankcardmanagementsystem.dto.request.CardBlockRequestDto;
import com.charniuk.bankcardmanagementsystem.dto.response.CardBlockResponse;

public interface CardBlockService {

  /**
   * Создание запроса на блокировку карты
   *
   * @return dto с информацией о созданном запросе
   */
  CardBlockResponse createBlockRequest(CardBlockRequestDto cardBlockRequestDto);
}
