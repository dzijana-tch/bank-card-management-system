package com.charniuk.bankcardmanagementsystem.service.impl;


import com.charniuk.bankcardmanagementsystem.dto.request.SignInRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.SignUpRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.JwtAuthenticationResponse;
import com.charniuk.bankcardmanagementsystem.enums.Role;
import com.charniuk.bankcardmanagementsystem.model.User;
import com.charniuk.bankcardmanagementsystem.security.JwtService;
import com.charniuk.bankcardmanagementsystem.service.AuthenticationService;
import com.charniuk.bankcardmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public JwtAuthenticationResponse signUp(SignUpRequest request) {

    var user = User.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .name(request.getName())
        .role(Role.ROLE_USER)
        .build();

    userService.create(user);

    var jwt = jwtService.generateToken(user);
    return new JwtAuthenticationResponse(jwt);
  }

  @Override
  public JwtAuthenticationResponse signIn(SignInRequest request) {

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getPassword()
    ));

    var user = userService
        .getByEmail(request.getEmail());

    var jwt = jwtService.generateToken(user);
    return new JwtAuthenticationResponse(jwt);
  }
}