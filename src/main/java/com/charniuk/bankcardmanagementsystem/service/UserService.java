package com.charniuk.bankcardmanagementsystem.service;

import com.charniuk.bankcardmanagementsystem.dto.request.UserRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.UserResponse;
import com.charniuk.bankcardmanagementsystem.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

  /**
   * Получение пользователя по электронной почте
   *
   * @return пользователь
   */
  User getByEmail(String email);

  /**
   * Получение пользователя по айди
   *
   * @return пользователь
   */
  User getByUserId(UUID userId);

  /**
   * Сервис для загрузки пользователя по электронной почте.
   * Используется Spring Security
   *
   * @return пользователь
   */
  UserDetailsService userDetailsService();

  /**
   * Создание пользователя
   *
   * @return созданный пользователь
   */
  User create(User user);

  /**
   * Создание пользователя
   *
   * @return dto созданного пользователя
   */
  UserResponse create(UserRequest userRequest);

  /**
   * Получение пользователя по айди
   *
   * @return dto пользователя
   */
  UserResponse getDtoByUserId(UUID userId);

  /**
   * Получение всех пользователей
   *
   * @return dto пользователей
   */
  List<UserResponse> getAllUserDto();

  /**
   * Обновление информации о пользователе
   *
   * @return dto пользователя
   */
  UserResponse update(UUID userId, UserRequest userRequest);

  /**
   * Удалить пользователя
   */
  void delete(UUID userId);
}

