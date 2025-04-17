package com.charniuk.bankcardmanagementsystem.dto.response;

import com.charniuk.bankcardmanagementsystem.enums.CardLimitType;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CardLimitResponse(
    UUID cardLimitId,
    UUID cardId,
    String cardNumber,
    CardLimitType type,
    BigDecimal amount
) {

}
