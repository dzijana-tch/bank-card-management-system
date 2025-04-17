package com.charniuk.bankcardmanagementsystem.model;

import com.charniuk.bankcardmanagementsystem.enums.CardStatus;
import com.charniuk.bankcardmanagementsystem.utils.CardNumberEncryptor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "card_id", nullable = false)
  private UUID cardId;

  @Column(name = "card_number", nullable = false, unique = true)
  @Convert(converter = CardNumberEncryptor.class)
  private String cardNumber;

  @Column(name = "card_holder_name", nullable = false)
  private String cardHolderName;

  @Column(name = "expiration_date", nullable = false)
  private LocalDate expirationDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CardStatus status;

  @Column(nullable = false)
  private BigDecimal balance;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "app_user_id", nullable = false)
  private User user;

  @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Transaction> transactions = new ArrayList<>();

  @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CardLimit> cardLimit = new ArrayList<>();
}