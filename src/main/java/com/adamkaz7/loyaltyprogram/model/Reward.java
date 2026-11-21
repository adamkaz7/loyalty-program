package com.adamkaz7.loyaltyprogram.model;

import com.adamkaz7.loyaltyprogram.dto.EarningRuleRequestDTO;
import com.adamkaz7.loyaltyprogram.dto.RewardRequestDTO;
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
@Table(name = "REWARDS")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private Integer pointsCost;
    @Column(nullable = false)
    private Long programId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
    private Integer availableQuantity;

    public void updateReward(RewardRequestDTO dto) {
        this.name = dto.name();
        this.description = dto.description();
        this.pointsCost = dto.pointsCost();
        this.startDate = dto.startDate();
        this.endDate = dto.endDate();
        this.active = dto.active();
        this.availableQuantity = dto.availableQuantity();
    }

    public boolean isAvailableOn(LocalDate date) {
        return active
                && (startDate == null || !startDate.isAfter(date))
                && (endDate == null || !date.isAfter(endDate))
                && (availableQuantity == null || availableQuantity > 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reward reward)) return false;
        return id != null && id.equals(reward.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

