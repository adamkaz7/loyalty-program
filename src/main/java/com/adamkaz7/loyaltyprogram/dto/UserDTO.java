package com.adamkaz7.loyaltyprogram.dto;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        LocalDateTime registeredAt
) {
}
