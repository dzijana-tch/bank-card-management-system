package com.charniuk.bankcardmanagementsystem.controller.impl;

import com.charniuk.bankcardmanagementsystem.controller.UserController;
import com.charniuk.bankcardmanagementsystem.dto.response.UserResponse;
import com.charniuk.bankcardmanagementsystem.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

  private final UserService userService;

  @GetMapping("/{user_id}")
  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserResponse> get(
      @PathVariable("user_id") UUID userId) {

    return ResponseEntity.ok(
        userService.getDtoByUserId(userId));
  }

  @GetMapping()
  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<UserResponse>> get() {
    return ResponseEntity.ok(
        userService.getAllUserDto());
  }

  @DeleteMapping("/{user_id}")
  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> delete(
      @PathVariable("user_id") UUID userId) {

    userService.delete(userId);
    return ResponseEntity.ok().build();
  }
}
