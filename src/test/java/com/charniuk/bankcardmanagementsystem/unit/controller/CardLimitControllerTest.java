package com.charniuk.bankcardmanagementsystem.unit.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.charniuk.bankcardmanagementsystem.controller.impl.CardLimitControllerImpl;
import com.charniuk.bankcardmanagementsystem.dto.request.CardLimitRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardLimitResponse;
import com.charniuk.bankcardmanagementsystem.enums.CardLimitType;
import com.charniuk.bankcardmanagementsystem.security.JwtAuthenticationFilter;
import com.charniuk.bankcardmanagementsystem.service.CardLimitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CardLimitControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
public class CardLimitControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private CardLimitService cardLimitService;

  @MockitoBean
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  private static final String CARD_LIMIT_CONTROLLER_URL = "/cards/limits";

  @Test
  @DisplayName("Limit setting is ok")
  void setLimit_is_ok() throws Exception {

    UUID cardId = UUID.randomUUID();
    CardLimitType cardLimitType = CardLimitType.DAILY;
    BigDecimal amount = BigDecimal.valueOf(1000);

    CardLimitRequest cardLimitRequest = CardLimitRequest.builder()
        .cardId(cardId)
        .type(cardLimitType)
        .amount(amount)
        .build();

    CardLimitResponse cardLimitResponse = CardLimitResponse.builder()
        .cardLimitId(UUID.randomUUID())
        .cardId(cardId)
        .cardNumber("1234567890123456")
        .type(cardLimitType)
        .amount(amount)
        .build();

    when(cardLimitService.setLimit(cardLimitRequest)).thenReturn(cardLimitResponse);

    mockMvc.perform(post(CARD_LIMIT_CONTROLLER_URL)
            .content(objectMapper.writeValueAsString(cardLimitRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(cardLimitResponse)));
  }
}
