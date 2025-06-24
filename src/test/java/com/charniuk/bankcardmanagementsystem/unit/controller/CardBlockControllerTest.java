package com.charniuk.bankcardmanagementsystem.unit.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.charniuk.bankcardmanagementsystem.controller.impl.CardBlockControllerImpl;
import com.charniuk.bankcardmanagementsystem.dto.request.CardBlockRequestDto;
import com.charniuk.bankcardmanagementsystem.dto.response.CardBlockResponse;
import com.charniuk.bankcardmanagementsystem.enums.BlockRequestStatus;
import com.charniuk.bankcardmanagementsystem.security.JwtAuthenticationFilter;
import com.charniuk.bankcardmanagementsystem.service.CardBlockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CardBlockControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
public class CardBlockControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private CardBlockService cardBlockService;

  @MockitoBean
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  private static final String CARD_BLOCK_CONTROLLER_URL = "/cards/block_requests";

  @Test
  @DisplayName("Create block request is ok")
  void createBlockRequest_is_ok() throws Exception {

    UUID cardId = UUID.randomUUID();
    String reason = "Подозрительные операции";

    CardBlockRequestDto cardBlockRequestDto = CardBlockRequestDto.builder()
        .cardId(cardId)
        .reason(reason)
        .build();

    CardBlockResponse cardBlockResponse = CardBlockResponse.builder()
        .requestId(UUID.randomUUID())
        .cardId(cardId)
        .cardNumber("1234567890123456")
        .status(BlockRequestStatus.PENDING)
        .reason(reason)
        .build();

    when(cardBlockService.createBlockRequest(cardBlockRequestDto)).thenReturn(cardBlockResponse);

    mockMvc.perform(post(CARD_BLOCK_CONTROLLER_URL)
            .content(objectMapper.writeValueAsString(cardBlockRequestDto))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(cardBlockResponse)));
  }
}
