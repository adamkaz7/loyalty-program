package com.adamkaz7.loyaltyprogram.model;

import com.adamkaz7.loyaltyprogram.dto.ProgramDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "LOYALTY_PROGRAMS")
public class LoyaltyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;
    @Column(name = "END_DATE")
    private LocalDate endDate;

    public void updateProgram(ProgramDTO dto) {
        this.name = dto.name();
        this.description = dto.description();
        this.startDate = dto.startDate();
        this.endDate = dto.endDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoyaltyProgram program)) return false;
        return id != null && id.equals(program.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
