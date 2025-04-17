package com.charniuk.bankcardmanagementsystem.dto.response;

import com.charniuk.bankcardmanagementsystem.enums.Role;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserResponse(
    UUID userId,
    String email,
    String name,
    Role role
) {

}