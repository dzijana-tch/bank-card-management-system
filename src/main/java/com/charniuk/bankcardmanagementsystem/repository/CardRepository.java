package com.charniuk.bankcardmanagementsystem.repository;

import com.charniuk.bankcardmanagementsystem.enums.CardStatus;
import com.charniuk.bankcardmanagementsystem.model.Card;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

  @Query("SELECT c FROM Card c WHERE " +
      "(:cardId IS NULL OR c.cardId = :cardId) AND " +
      "(:status IS NULL OR c.status = :status) AND " +
      "(:cardHolderName IS NULL OR UPPER(c.cardHolderName) LIKE %:cardHolderName%)")
  List<Card> findFilteredCards(@Param("cardId") UUID cardId,
      @Param("cardHolderName") String cardHolderName, @Param("status") CardStatus status,
      Pageable pageable);

}
