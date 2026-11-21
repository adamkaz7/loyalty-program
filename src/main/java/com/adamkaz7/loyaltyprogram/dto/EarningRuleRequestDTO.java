package com.adamkaz7.loyaltyprogram.dto;

import com.adamkaz7.loyaltyprogram.model.EarningEventType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EarningRuleRequestDTO(
        @NotBlank String name,
        @NotNull EarningEventType eventType,
        @NotNull @Min(1) Integer points,
        LocalDate startDate,
        LocalDate endDate,
        boolean active
) {
}
