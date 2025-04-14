package com.charniuk.bankcardmanagementsystem.service.impl;

import com.charniuk.bankcardmanagementsystem.exceptions.UserAlreadyExistsException;
import com.charniuk.bankcardmanagementsystem.exceptions.UserNotFoundException;
import com.charniuk.bankcardmanagementsystem.model.User;
import com.charniuk.bankcardmanagementsystem.repository.UserRepository;
import com.charniuk.bankcardmanagementsystem.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  @Override
  @Transactional
  public User create(User user) {
    if (repository.existsByEmail(user.getEmail())) {
      throw new UserAlreadyExistsException(
          "Пользователь с email " + user.getEmail() + " уже существует");
    }
    return repository.save(user);
  }

  @Override
  public User getByEmail(String email) {
    return repository.findByEmail(email)
        .orElseThrow(
            () -> new UserNotFoundException("Пользователь с email " + email + " не найден"));
  }

  @Override
  public User getByUserId(UUID userId) {
    return repository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + userId + " не найден"));
  }

  @Override
  public UserDetailsService userDetailsService() {
    return this::getByEmail;
  }
}
