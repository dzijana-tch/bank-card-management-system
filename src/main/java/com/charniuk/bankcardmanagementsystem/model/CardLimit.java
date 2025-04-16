package com.charniuk.bankcardmanagementsystem.model;

import com.charniuk.bankcardmanagementsystem.enums.CardLimitType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "card_limits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardLimit {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "card_limit_id", nullable = false)
  private UUID cardLimitId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id", nullable = false)
  private Card card;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CardLimitType type;

  @Column(nullable = false)
  private BigDecimal amount;
}
