package com.adamkaz7.loyaltyprogram.repository;

import com.adamkaz7.loyaltyprogram.model.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long> {
    Page<Reward> findByProgramId(Long programId, Pageable pageable);
}
