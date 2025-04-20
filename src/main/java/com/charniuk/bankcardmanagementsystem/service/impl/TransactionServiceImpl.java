package com.charniuk.bankcardmanagementsystem.service.impl;

import com.charniuk.bankcardmanagementsystem.dto.request.TransactionFilterRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.TransferRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.WithdrawalRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.TransactionResponse;
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
import com.charniuk.bankcardmanagementsystem.service.TransactionService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;
  private final CardService cardService;
  private final CardLimitService cardLimitService;

  @Override
  @Transactional(readOnly = true)
  public List<TransactionResponse> getAllTransactions(
      TransactionFilterRequest transactionFilterRequest, Pageable pageable) {
    List<Transaction> transactions = transactionRepository.findFilteredTransactions(
        transactionFilterRequest.getType(), transactionFilterRequest.getCardId(), pageable);
    return transactionMapper.toResponse(transactions);
  }

  @Override
  @Transactional
  public void makeTransfer(TransferRequest transferRequest) {

    UUID senderCardId = transferRequest.getSenderCardId();
    UUID recipientCardId = transferRequest.getRecipientCardId();
    BigDecimal amount = transferRequest.getAmount();

    Card senderCard = cardService.getByCardId(senderCardId);
    Card recipientCard = cardService.getByCardId(recipientCardId);

    isTransactionPossible(senderCard, amount);
    senderCard.setBalance(senderCard.getBalance().subtract(amount));

    recipientCard.setBalance(recipientCard.getBalance().add(amount));

    Transaction transaction = Transaction.builder()
        .amount(amount)
        .description("Перевод средств на карту " + recipientCard.getCardNumber())
        .type(TransactionType.TRANSFER_OUT)
        .card(senderCard)
        .build();

    transactionRepository.save(transaction);
  }

  @Override
  @Transactional
  public void withdrawFromCard(WithdrawalRequest withdrawalRequest) {

    UUID cardId = withdrawalRequest.getCardId();
    BigDecimal amount = withdrawalRequest.getAmount();

    Card card = cardService.getByCardId(cardId);
    isTransactionPossible(card, amount);

    card.setBalance(card.getBalance().subtract(amount));

    Transaction transaction = Transaction.builder()
        .amount(amount)
        .description("Снятие средств")
        .type(TransactionType.WITHDRAWAL)
        .card(card)
        .build();

    transactionRepository.save(transaction);
  }

  /**
   * Возможна ли операция по карте
   */
  private void isTransactionPossible(Card card, BigDecimal amount) {

    isActive(card);
    hasSufficientFunds(card, amount);

    List<CardLimit> limits = cardLimitService.getLimitsByCard(card);
    limits.forEach(l -> checkLimit(l, amount));
  }

  /**
   * Активна ли карта
   */
  private void isActive(Card card) {
    if (!card.getStatus().equals(CardStatus.ACTIVE)) {
      throw new CardNotActiveException("Карта не активна");
    }
  }

  /**
   * Достаточно ли средств на балансе
   */
  private void hasSufficientFunds(Card card, BigDecimal amount) {
    if (card.getBalance().compareTo(amount) < 0) {
      throw new InsufficientFundsException("Недостаточно средств на балансе");
    }
  }

  /**
   * Проверка лимита
   */
  private void checkLimit(CardLimit cardLimit, BigDecimal newTransactionAmount) {

    LocalDateTime date = switch (cardLimit.getType()) {
      case DAILY -> LocalDateTime.now().minusDays(1);
      case MONTHLY -> LocalDateTime.now().minusMonths(1);
    };

    List<Transaction> transactions = transactionRepository.findTransactionsAfterDate(
        cardLimit.getCard(), date);

    BigDecimal sum = transactions.stream()
        .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) < 0)
        .map(t -> t.getAmount().abs())
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);

    BigDecimal totalAmount = sum.add(newTransactionAmount);

    if (totalAmount.compareTo(cardLimit.getAmount()) > 0) {
      throw new CardLimitExceededException("Превышен лимит по карте");
    }
  }


}
