package com.charniuk.bankcardmanagementsystem.service;

import com.charniuk.bankcardmanagementsystem.dto.request.SignInRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.SignUpRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {

  /**
   * Регистрация пользователя
   *
   * @param request данные пользователя
   * @return токен
   */
  JwtAuthenticationResponse signUp(SignUpRequest request);

  /**
   * Аутентификация пользователя
   *
   * @param request данные пользователя
   * @return токен
   */
  JwtAuthenticationResponse signIn(SignInRequest request);
}