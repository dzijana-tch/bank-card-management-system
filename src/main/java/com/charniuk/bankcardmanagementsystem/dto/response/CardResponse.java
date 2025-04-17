package com.charniuk.bankcardmanagementsystem.dto.response;

import com.charniuk.bankcardmanagementsystem.enums.CardStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CardResponse(
    UUID cardId,
    String cardNumber,
    String cardHolderName,
    LocalDate expirationDate,
    CardStatus status,
    BigDecimal balance,
    UserResponse userResponse
) {

}
