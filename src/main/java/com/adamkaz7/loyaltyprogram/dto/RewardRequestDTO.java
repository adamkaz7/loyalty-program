package com.adamkaz7.loyaltyprogram.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RewardRequestDTO(
        @NotBlank String name,
        String description,
        @NotNull @Min(1) Integer pointsCost,
        LocalDate startDate,
        LocalDate endDate,
        @NotNull Boolean active,
        @Min(0) Integer availableQuantity
) {
}
