package com.charniuk.bankcardmanagementsystem.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.charniuk.bankcardmanagementsystem.controller.impl.TransactionControllerImpl;
import com.charniuk.bankcardmanagementsystem.dto.request.TransferRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.WithdrawalRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.TransactionResponse;
import com.charniuk.bankcardmanagementsystem.enums.TransactionType;
import com.charniuk.bankcardmanagementsystem.security.JwtAuthenticationFilter;
import com.charniuk.bankcardmanagementsystem.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

@WebMvcTest(TransactionControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
public class TransactionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private TransactionService transactionService;

  @MockitoBean
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  private static final String TRANSACTION_CONTROLLER_URL = "/transactions";

  @Test
  @DisplayName("Get transactions is ok")
  void get_is_ok() throws Exception {

    TransactionType transactionType = TransactionType.WITHDRAWAL;
    UUID cardId = UUID.randomUUID();
    String sortDirection = "ASC";
    String sortBy = "expirationDate";
    Integer offset = 0;
    Integer limit = 10;

    List<TransactionResponse> transactions = new ArrayList<>();
    transactions.add(buildTransactionResponse());

    when(transactionService.getAllTransactions(any(), any(), any())).thenReturn(transactions);

    mockMvc.perform(get(TRANSACTION_CONTROLLER_URL)
            .param("transactionType", transactionType.toString())
            .param("cardId", cardId.toString())
            .param("sortDirection", sortDirection)
            .param("sortBy", sortBy)
            .param("offset", offset.toString())
            .param("limit", limit.toString()))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(transactions)));

  }

  @Test
  @DisplayName("Making transfer is ok")
  void makeTransfer_is_ok() throws Exception {

    TransferRequest transferRequest = buildTransferRequest();

    mockMvc.perform(post(TRANSACTION_CONTROLLER_URL + "/transfer")
            .content(objectMapper.writeValueAsString(transferRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Withdraw from card is ok")
  void withdrawFromCard_is_ok() throws Exception {

    WithdrawalRequest withdrawalRequest = buildWithdrawalRequest();

    mockMvc.perform(post(TRANSACTION_CONTROLLER_URL + "/withdrawal")
            .content(objectMapper.writeValueAsString(withdrawalRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  private TransactionResponse buildTransactionResponse() {
    return TransactionResponse.builder()
        .transactionId(UUID.randomUUID())
        .amount(BigDecimal.ONE)
        .description("Снятие средств")
        .transactionTimestamp(LocalDateTime.now())
        .type(TransactionType.WITHDRAWAL)
        .build();
  }

  private TransferRequest buildTransferRequest() {
    return TransferRequest.builder()
        .senderCardId(UUID.randomUUID())
        .recipientCardId(UUID.randomUUID())
        .amount(BigDecimal.ONE)
        .build();
  }

  private WithdrawalRequest buildWithdrawalRequest() {
    return WithdrawalRequest.builder()
        .cardId(UUID.randomUUID())
        .amount(BigDecimal.ONE)
        .build();
  }
}
