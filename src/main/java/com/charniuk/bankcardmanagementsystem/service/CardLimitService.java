package com.charniuk.bankcardmanagementsystem.service;

import com.charniuk.bankcardmanagementsystem.dto.request.CardLimitRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardLimitResponse;
import com.charniuk.bankcardmanagementsystem.model.Card;
import com.charniuk.bankcardmanagementsystem.model.CardLimit;
import java.util.List;

public interface CardLimitService {

  /**
   * Создание лимита по карте
   *
   * @return dto с информацией о созданном лимите
   */
  CardLimitResponse setLimit(CardLimitRequest cardLimitRequest);

  /**
   * Получение лимитов по карте
   *
   * @return информация о лимите
   */
  List<CardLimit> getLimitsByCard(Card card);

}
