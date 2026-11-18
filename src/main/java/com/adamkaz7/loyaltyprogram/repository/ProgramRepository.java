package com.adamkaz7.loyaltyprogram.repository;

import com.adamkaz7.loyaltyprogram.model.LoyaltyProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ProgramRepository extends JpaRepository<LoyaltyProgram, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
    @Query("SELECT p FROM LoyaltyProgram p WHERE p.startDate <= :date AND (p.endDate IS NULL OR p.endDate >= :date)")
    Page<LoyaltyProgram> findActiveOn(LocalDate date, Pageable pageable);
}
