package com.adamkaz7.loyaltyprogram.repository;

import com.adamkaz7.loyaltyprogram.model.EarningEventType;
import com.adamkaz7.loyaltyprogram.model.EarningRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EarningRuleRepository extends JpaRepository<EarningRule, Long> {
    Page<EarningRule> findByProgramId(Long programId, Pageable pageable);
    boolean existsByProgramIdAndEventType(Long programId, EarningEventType eventType);
    boolean existsByProgramIdAndEventTypeAndIdNot(Long programId, EarningEventType eventType, Long id);
    Optional<EarningRule> findByProgramIdAndEventType(Long programId, EarningEventType eventType);
}
