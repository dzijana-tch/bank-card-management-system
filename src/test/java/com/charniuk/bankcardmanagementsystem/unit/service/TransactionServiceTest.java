package com.charniuk.bankcardmanagementsystem.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.charniuk.bankcardmanagementsystem.dto.request.TransferRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.WithdrawalRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.TransactionResponse;
import com.charniuk.bankcardmanagementsystem.enums.CardLimitType;
import com.charniuk.bankcardmanagementsystem.enums.CardStatus;
import com.charniuk.bankcardmanagementsystem.enums.TransactionType;
import com.charniuk.bankcardmanagementsystem.exceptions.CardLimitExceededException;
import com.charniuk.bankcardmanagementsystem.exceptions.CardNotActiveException;
import com.charniuk.bankcardmanagementsystem.exceptions.InsufficientFundsException;
import com.charniuk.bankcardmanagementsystem.mapper.TransactionMapper;
import com.charniuk.bankcardmanagementsystem.model.Card;
import com.charniuk.bankcardmanagementsystem.model.CardLimit;
import com.charniuk.bankcardmanagementsystem.model.Transaction;
import com.charniuk.bankcardmanagementsystem.repository.TransactionRepository;
import com.charniuk.bankcardmanagementsystem.service.CardLimitService;
import com.charniuk.bankcardmanagementsystem.service.CardService;
import com.charniuk.bankcardmanagementsystem.service.impl.TransactionServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

  @InjectMocks
  public TransactionServiceImpl transactionService;

  @Mock
  private TransactionRepository transactionRepository;

  @Mock
  private TransactionMapper transactionMapper;

  @Mock
  private CardService cardService;

  @Mock
  private CardLimitService cardLimitService;

  @Test
  @DisplayName("Getting all transactions is ok")
  void getAllTransactions_is_ok() {

    TransactionType type = TransactionType.DEPOSIT;
    UUID cardId = UUID.randomUUID();
    Pageable pageable = PageRequest.of(
        0, 10, Sort.by("transactionTimestamp").descending());

    Transaction transaction1 = new Transaction();
    Transaction transaction2 = new Transaction();
    Transaction transaction3 = new Transaction();
    List<Transaction> transactions = List.of(transaction1, transaction2, transaction3);

    TransactionResponse transactionResponse1 = TransactionResponse.builder().build();
    TransactionResponse transactionResponse2 = TransactionResponse.builder().build();
    TransactionResponse transactionResponse3 = TransactionResponse.builder().build();
    List<TransactionResponse> transactionResponses = List.of(transactionResponse1,
        transactionResponse2, transactionResponse3);

    when(transactionRepository.findFilteredTransactions(type, cardId, pageable)).thenReturn(
        transactions);
    when(transactionMapper.toResponse(transactions)).thenReturn(transactionResponses);

    assertEquals(transactionResponses,
        transactionService.getAllTransactions(type, cardId, pageable));

  }

  @Test
  @DisplayName("Making transfer is ok")
  void makeTransfer_is_ok() {

    UUID senderCardId = UUID.randomUUID();
    UUID recipientCardId = UUID.randomUUID();
    BigDecimal amount = BigDecimal.valueOf(100);

    TransferRequest transferRequest = TransferRequest.builder()
        .senderCardId(senderCardId)
        .recipientCardId(recipientCardId)
        .amount(amount)
        .build();

    Card senderCard = Card.builder()
        .cardId(senderCardId)
        .status(CardStatus.ACTIVE)
        .balance(BigDecimal.valueOf(1000))
        .build();
    Card recipientCard = Card.builder()
        .cardId(recipientCardId)
        .cardNumber("1111111111111111")
        .balance(BigDecimal.valueOf(0))
        .build();

    when(cardService.getByCardId(senderCardId)).thenReturn(senderCard);
    when(cardService.getByCardId(recipientCardId)).thenReturn(recipientCard);

    transactionService.makeTransfer(transferRequest);

    verify(transactionRepository, times(2)).save(any());
    assertEquals(BigDecimal.valueOf(1000 - 100), senderCard.getBalance());
    assertEquals(BigDecimal.valueOf(100), recipientCard.getBalance());
  }

  @Test
  @DisplayName("Making transfer when card is not active")
  void makeTransfer_when_card_is_not_active() {

    UUID senderCardId = UUID.randomUUID();
    UUID recipientCardId = UUID.randomUUID();
    BigDecimal amount = BigDecimal.valueOf(100);

    TransferRequest transferRequest = TransferRequest.builder()
        .senderCardId(senderCardId)
        .recipientCardId(recipientCardId)
        .amount(amount)
        .build();

    Card senderCard = Card.builder()
        .cardId(senderCardId)
        .status(CardStatus.BLOCKED)
        .balance(BigDecimal.valueOf(1000))
        .build();
    Card recipientCard = Card.builder()
        .cardId(recipientCardId)
        .balance(BigDecimal.valueOf(0))
        .build();

    when(cardService.getByCardId(senderCardId)).thenReturn(senderCard);
    when(cardService.getByCardId(recipientCardId)).thenReturn(recipientCard);

    assertThrows(CardNotActiveException.class,
        () -> transactionService.makeTransfer(transferRequest));
  }

  @Test
  @DisplayName("Making transfer when card hasn't sufficient funds")
  void makeTransfer_when_card_has_not_sufficient_funds() {

    UUID senderCardId = UUID.randomUUID();
    UUID recipientCardId = UUID.randomUUID();
    BigDecimal amount = BigDecimal.valueOf(100);

    TransferRequest transferRequest = TransferRequest.builder()
        .senderCardId(senderCardId)
        .recipientCardId(recipientCardId)
        .amount(amount)
        .build();

    Card senderCard = Card.builder()
        .cardId(senderCardId)
        .status(CardStatus.ACTIVE)
        .balance(BigDecimal.valueOf(0))
        .build();
    Card recipientCard = Card.builder()
        .cardId(recipientCardId)
        .balance(BigDecimal.valueOf(0))
        .build();

    when(cardService.getByCardId(senderCardId)).thenReturn(senderCard);
    when(cardService.getByCardId(recipientCardId)).thenReturn(recipientCard);

    assertThrows(InsufficientFundsException.class,
        () -> transactionService.makeTransfer(transferRequest));
  }

  @Test
  @DisplayName("Making transfer is not active")
  void makeTransfer_when_card_limit_exceeded() {

    UUID senderCardId = UUID.randomUUID();
    UUID recipientCardId = UUID.randomUUID();
    BigDecimal amount = BigDecimal.valueOf(100);

    TransferRequest transferRequest = TransferRequest.builder()
        .senderCardId(senderCardId)
        .recipientCardId(recipientCardId)
        .amount(amount)
        .build();

    Card senderCard = Card.builder()
        .cardId(senderCardId)
        .status(CardStatus.ACTIVE)
        .balance(BigDecimal.valueOf(1000))
        .build();
    Card recipientCard = Card.builder()
        .cardId(recipientCardId)
        .balance(BigDecimal.valueOf(0))
        .build();

    CardLimit cardLimit = CardLimit.builder()
        .card(senderCard)
        .type(CardLimitType.DAILY)
        .amount(BigDecimal.valueOf(500))
        .build();

    Transaction transaction = Transaction.builder()
        .amount(BigDecimal.valueOf(-500))
        .build();

    when(cardService.getByCardId(senderCardId)).thenReturn(senderCard);
    when(cardService.getByCardId(recipientCardId)).thenReturn(recipientCard);

    when(cardLimitService.getLimitsByCard(senderCard)).thenReturn(List.of(cardLimit));
    when(transactionRepository.findTransactionsAfterDate(eq(senderCard), any())).thenReturn(
        List.of(transaction));

    assertThrows(CardLimitExceededException.class,
        () -> transactionService.makeTransfer(transferRequest));
  }

  @Test
  @DisplayName("Withdrawing from card is ok")
  void withdrawFromCard_is_ok() {

    UUID cardId = UUID.randomUUID();
    BigDecimal amount = BigDecimal.valueOf(1000);

    WithdrawalRequest withdrawalRequest = WithdrawalRequest.builder()
        .cardId(cardId)
        .amount(amount)
        .build();

    Card card = Card.builder()
        .cardId(cardId)
        .status(CardStatus.ACTIVE)
        .balance(BigDecimal.valueOf(1000))
        .build();

    when(cardService.getByCardId(cardId)).thenReturn(card);

    transactionService.withdrawFromCard(withdrawalRequest);

    verify(transactionRepository, times(1)).save(any());
    assertEquals(BigDecimal.valueOf(0), card.getBalance());
  }
}
