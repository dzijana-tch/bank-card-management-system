package com.charniuk.bankcardmanagementsystem.repository;

import com.charniuk.bankcardmanagementsystem.enums.TransactionType;
import com.charniuk.bankcardmanagementsystem.model.Card;
import com.charniuk.bankcardmanagementsystem.model.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

  @Query("SELECT t FROM Transaction t WHERE t.card = :card AND t.transactionTimestamp >= :date")
  List<Transaction> findTransactionsAfterDate(@Param("card") Card card,
      @Param("date") LocalDateTime date);

  @Query("SELECT t FROM Transaction t WHERE " +
      "(:cardId IS NULL OR t.card.cardId = :cardId) AND " +
      "(:type IS NULL OR t.type = :type)")
  List<Transaction> findFilteredTransactions(@Param("type") TransactionType type,
      @Param("cardId") UUID cardId, Pageable pageable);
}
