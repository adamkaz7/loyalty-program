package com.adamkaz7.loyaltyprogram.model;

import com.adamkaz7.loyaltyprogram.dto.EarningRuleRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "EARNING_RULES")
public class EarningRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EarningEventType eventType;
    @Column(nullable = false)
    private Integer points;
    @Column(nullable = false)
    private Long programId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public void updateRule(EarningRuleRequestDTO dto) {
        this.name = dto.name();
        this.eventType = dto.eventType();
        this.points = dto.points();
        this.startDate = dto.startDate();
        this.endDate = dto.endDate();
        this.active = dto.active();
    }

    public boolean isApplicableOn(LocalDate date) {
        return active
                && (startDate == null || !startDate.isAfter(date))
                && (endDate == null || !date.isAfter(endDate));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EarningRule rule)) return false;
        return id != null && id.equals(rule.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
