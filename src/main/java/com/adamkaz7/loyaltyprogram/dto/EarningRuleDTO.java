package com.adamkaz7.loyaltyprogram.dto;

import com.adamkaz7.loyaltyprogram.model.EarningEventType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EarningRuleDTO(
        Long id,
        String name,
        EarningEventType eventType,
        Integer points,
        Long programId,
        LocalDate startDate,
        LocalDate endDate,
        @NotNull boolean active
) {
}
