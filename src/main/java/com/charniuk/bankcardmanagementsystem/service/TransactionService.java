package com.charniuk.bankcardmanagementsystem.service;

import com.charniuk.bankcardmanagementsystem.dto.request.TransactionFilterRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.TransactionResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

  /**
   * Получить все транзакции (с пагинацией)
   *
   * @return List dto с информацией о транзакциях
   */
  List<TransactionResponse> getAllTransactions(TransactionFilterRequest transactionFilterRequest,
      Pageable pageable);

  /**
   * Совершить перевод между картами
   */
  void makeTransfer(UUID senderCardId, UUID recipientCardId, BigDecimal amount);

  /**
   * Списать средства с карты (с проверкой лимита)
   */
  void withdrawFromCard(UUID cardId, BigDecimal amount);

}
