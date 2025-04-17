package com.charniuk.bankcardmanagementsystem.dto.response;

import com.charniuk.bankcardmanagementsystem.enums.BlockRequestStatus;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CardBlockResponse(
    UUID requestId,
    UUID cardId,
    String cardNumber,
    BlockRequestStatus status,
    String reason
) {

}
