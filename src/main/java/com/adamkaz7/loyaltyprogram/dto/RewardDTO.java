package com.adamkaz7.loyaltyprogram.dto;

import java.time.LocalDate;

public record RewardDTO(
        Long id,
        String name,
        String description,
        Integer pointsCost,
        Long programId,
        LocalDate startDate,
        LocalDate endDate,
        Boolean active,
        Integer availableQuantity,
        boolean available
) {
}
