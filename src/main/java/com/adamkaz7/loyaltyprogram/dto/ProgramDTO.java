package com.adamkaz7.loyaltyprogram.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProgramDTO(
        Long id,
        @NotBlank String name,
        String description,
        @NotNull LocalDate startDate,
        LocalDate endDate
) {
}
