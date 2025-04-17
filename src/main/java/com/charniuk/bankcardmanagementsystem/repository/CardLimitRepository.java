package com.charniuk.bankcardmanagementsystem.repository;

import com.charniuk.bankcardmanagementsystem.model.Card;
import com.charniuk.bankcardmanagementsystem.model.CardLimit;
import com.charniuk.bankcardmanagementsystem.enums.CardLimitType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardLimitRepository extends JpaRepository<CardLimit, UUID> {

  boolean existsByCard_CardIdAndType(UUID cardId, CardLimitType type);

  List<CardLimit> findByCard(Card card);
}

