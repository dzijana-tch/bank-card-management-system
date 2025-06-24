package com.charniuk.bankcardmanagementsystem.dto.response;

import com.charniuk.bankcardmanagementsystem.enums.BlockRequestStatus;
import com.charniuk.bankcardmanagementsystem.utils.MaskedCardSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CardBlockResponse(
    UUID requestId,
    UUID cardId,
    @JsonSerialize(using = MaskedCardSerializer.class)
    String cardNumber,
    BlockRequestStatus status,
    String reason
) {

}
