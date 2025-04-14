package com.charniuk.bankcardmanagementsystem.security;

import com.charniuk.bankcardmanagementsystem.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  /**
   * Извлечение айди пользователя из токена
   *
   * @param token токен
   * @return электронная почта
   */
  String extractEmail(String token);

  /**
   * Генерация токена
   *
   * @param user данные пользователя
   * @return токен
   */
  String generateToken(User user);

  /**
   * Проверка токена на валидность
   *
   * @param token       токен
   * @param userDetails данные пользователя
   * @return true, если токен валиден
   */
  boolean isTokenValid(String token, UserDetails userDetails);

}
