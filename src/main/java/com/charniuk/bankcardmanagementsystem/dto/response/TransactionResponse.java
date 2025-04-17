package com.charniuk.bankcardmanagementsystem.dto.response;

import com.charniuk.bankcardmanagementsystem.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record TransactionResponse(
    UUID transactionId,
    BigDecimal amount,
    String description,
    LocalDateTime transactionTimestamp,
    TransactionType type
) {

}
