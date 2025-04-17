package com.charniuk.bankcardmanagementsystem.model;

import com.charniuk.bankcardmanagementsystem.enums.BlockRequestStatus;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "card_block_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardBlockRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "request_id", nullable = false)
  private UUID requestId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id", nullable = false)
  private Card card;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BlockRequestStatus status;

  @Column(nullable = false)
  private String reason;

  @Column(name = "requested_at", nullable = false)
  private LocalDateTime requestedAt;

  @Column(name = "processed_at")
  private LocalDateTime processedAt;

  @PrePersist
  public void setRequestedAt() {
    this.requestedAt = LocalDateTime.now();
  }

  @PreUpdate
  public void setProcessedAt() {
    this.processedAt = LocalDateTime.now();
  }
}
