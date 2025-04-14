package com.charniuk.bankcardmanagementsystem.controller.impl;

import com.charniuk.bankcardmanagementsystem.controller.AuthController;
import com.charniuk.bankcardmanagementsystem.dto.request.SignInRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.SignUpRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.JwtAuthenticationResponse;
import com.charniuk.bankcardmanagementsystem.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

  private final AuthenticationService authenticationService;

  @Override
  @PostMapping("/sign-up")
  public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest request) {
    return ResponseEntity.ok(authenticationService.signUp(request));
  }

  @Override
  @PostMapping("/sign-in")
  public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) {
    return ResponseEntity.ok(authenticationService.signIn(request));
  }
}