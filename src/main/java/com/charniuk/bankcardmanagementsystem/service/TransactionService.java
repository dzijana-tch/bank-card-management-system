package com.charniuk.bankcardmanagementsystem.service;

import com.charniuk.bankcardmanagementsystem.dto.request.TransactionFilterRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.TransferRequest;
import com.charniuk.bankcardmanagementsystem.dto.request.WithdrawalRequest;
import com.charniuk.bankcardmanagementsystem.dto.response.TransactionResponse;
import java.util.List;
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
   * Совершить перевод между картами (с проверкой лимита)
   */
  void makeTransfer(TransferRequest transferRequest);

  /**
   * Списать средства с карты (с проверкой лимита)
   */
  void withdrawFromCard(WithdrawalRequest withdrawalRequest);

}
