package com.charniuk.bankcardmanagementsystem.service.impl;

import com.charniuk.bankcardmanagementsystem.dto.response.UserResponse;
import com.charniuk.bankcardmanagementsystem.exceptions.UserAlreadyExistsException;
import com.charniuk.bankcardmanagementsystem.exceptions.UserNotFoundException;
import com.charniuk.bankcardmanagementsystem.mapper.UserMapper;
import com.charniuk.bankcardmanagementsystem.model.User;
import com.charniuk.bankcardmanagementsystem.repository.UserRepository;
import com.charniuk.bankcardmanagementsystem.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public User getByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(
            () -> new UserNotFoundException("Пользователь с email " + email + " не найден"));
  }

  @Override
  @Transactional(readOnly = true)
  public User getByUserId(UUID userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + userId + " не найден"));
  }

  @Override
  public UserDetailsService userDetailsService() {
    return this::getByEmail;
  }

  @Override
  @Transactional
  public User create(User user) {
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new UserAlreadyExistsException(
          "Пользователь с email " + user.getEmail() + " уже существует");
    }
    return userRepository.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponse getDtoByUserId(UUID userId) {
    User user = getByUserId(userId);
    return userMapper.toResponse(user);
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserResponse> getAllUserDto() {
    List<User> users = userRepository.findAll();
    return userMapper.toResponse(users);
  }

  @Override
  @Transactional
  public void delete(UUID userId) {
    User user = getByUserId(userId);
    userRepository.delete(user);
  }
}
