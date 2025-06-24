package com.charniuk.bankcardmanagementsystem.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.charniuk.bankcardmanagementsystem.controller.impl.CardControllerImpl;
import com.charniuk.bankcardmanagementsystem.dto.request.CardRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.CardResponse;
import com.charniuk.bankcardmanagementsystem.dto.response.UserResponse;
import com.charniuk.bankcardmanagementsystem.enums.CardStatus;
import com.charniuk.bankcardmanagementsystem.enums.Role;
import com.charniuk.bankcardmanagementsystem.exceptions.CardNotFoundException;
import com.charniuk.bankcardmanagementsystem.exceptions.UserNotFoundException;
import com.charniuk.bankcardmanagementsystem.security.JwtAuthenticationFilter;
import com.charniuk.bankcardmanagementsystem.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CardControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
public class CardControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private CardService cardService;

  @MockitoBean
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  private static final String CARD_CONTROLLER_URL = "/cards";

  @Test
  @DisplayName("Create card is ok")
  void create_is_ok() throws Exception {

    CardRequest cardRequest = buildCardRequest();
    CardResponse cardResponse = buildCardResponse();

    when(cardService.create(cardRequest)).thenReturn(cardResponse);

    mockMvc.perform(post(CARD_CONTROLLER_URL)
            .content(objectMapper.writeValueAsString(cardRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(cardResponse)));
  }

  @Test
  @DisplayName("Create card when user is not found")
  void create_not_found() throws Exception {

    CardRequest cardRequest = buildCardRequest();

    when(cardService.create(cardRequest)).thenThrow(UserNotFoundException.class);

    mockMvc.perform(post(CARD_CONTROLLER_URL)
            .content(objectMapper.writeValueAsString(cardRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Block card is ok")
  void block_is_ok() throws Exception {

    UUID cardId = UUID.randomUUID();
    CardResponse cardResponse = buildCardResponse();

    when(cardService.block(cardId)).thenReturn(cardResponse);

    mockMvc.perform(patch(CARD_CONTROLLER_URL + "/blocking/{card_id}", cardId))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(cardResponse)));
  }

  @Test
  @DisplayName("Block card when card is not found")
  void block_not_found() throws Exception {

    UUID cardId = UUID.randomUUID();

    when(cardService.block(cardId)).thenThrow(CardNotFoundException.class);

    mockMvc.perform(patch(CARD_CONTROLLER_URL + "/blocking/{card_id}", cardId))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Activate card is ok")
  void activate_is_ok() throws Exception {

    UUID cardId = UUID.randomUUID();
    CardResponse cardResponse = buildCardResponse();

    when(cardService.activate(cardId)).thenReturn(cardResponse);

    mockMvc.perform(patch(CARD_CONTROLLER_URL + "/activation/{card_id}", cardId))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(cardResponse)));
  }

  @Test
  @DisplayName("Activate card when card is not found")
  void activate_not_found() throws Exception {

    UUID cardId = UUID.randomUUID();

    when(cardService.activate(cardId)).thenThrow(CardNotFoundException.class);

    mockMvc.perform(patch(CARD_CONTROLLER_URL + "/activating/{card_id}", cardId))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Delete card is ok")
  void delete_is_ok() throws Exception {

    UUID cardId = UUID.randomUUID();
    CardResponse cardResponse = buildCardResponse();

    when(cardService.delete(cardId)).thenReturn(cardResponse);

    mockMvc.perform(delete(CARD_CONTROLLER_URL + "/{card_id}", cardId))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(cardResponse)));
  }

  @Test
  @DisplayName("Delete card when card is not found")
  void delete_not_found() throws Exception {

    UUID cardId = UUID.randomUUID();

    when(cardService.delete(cardId)).thenThrow(CardNotFoundException.class);

    mockMvc.perform(delete(CARD_CONTROLLER_URL + "/{card_id}", cardId))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Get card is ok")
  void get_is_ok() throws Exception {

    UUID userId = UUID.randomUUID();
    String cardHolderName = "IVAN IVANOV";
    CardStatus status = CardStatus.ACTIVE;
    String sortDirection = "ASC";
    String sortBy = "expirationDate";
    Integer offset = 0;
    Integer limit = 10;

    List<CardResponse> cards = new ArrayList<>();
    cards.add(buildCardResponse());

    when(cardService.getAllCards(any(), any(), any(), any())).thenReturn(cards);

    mockMvc.perform(get(CARD_CONTROLLER_URL)
            .param("userId", userId.toString())
            .param("cardHolderName", cardHolderName)
            .param("status", status.toString())
            .param("sortDirection", sortDirection)
            .param("sortBy", sortBy)
            .param("offset", offset.toString())
            .param("limit", limit.toString()))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(cards)));

  }

  private CardRequest buildCardRequest() {
    return CardRequest.builder()
        .cardNumber("1234567890123456")
        .cardHolderName("IVAN IVANOV")
        .expirationDate(LocalDate.now().plusYears(2))
        .status(CardStatus.ACTIVE)
        .balance(BigDecimal.ZERO)
        .userId(UUID.randomUUID())
        .build();
  }

  private CardResponse buildCardResponse() {

    UserResponse userResponse = UserResponse.builder()
        .userId(UUID.randomUUID())
        .email("ivanov@mail.tu")
        .name("Иван Иванов")
        .role(Role.ROLE_USER)
        .build();

    return CardResponse.builder()
        .cardId(UUID.randomUUID())
        .cardNumber("1234567890123456")
        .cardHolderName("IVAN IVANOV")
        .expirationDate(LocalDate.now().plusYears(2))
        .status(CardStatus.ACTIVE)
        .balance(BigDecimal.ZERO)
        .userResponse(userResponse)
        .build();
  }

}