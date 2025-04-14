package com.charniuk.bankcardmanagementsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ErrorResponse(
    String uri,
    String type,
    String message,
    Long timestamp
) {

}
