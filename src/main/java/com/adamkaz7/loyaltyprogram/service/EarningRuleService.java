package com.adamkaz7.loyaltyprogram.service;

import com.adamkaz7.loyaltyprogram.dto.EarningRuleDTO;
import com.adamkaz7.loyaltyprogram.dto.EarningRuleRequestDTO;
import com.adamkaz7.loyaltyprogram.exception.BusinessException;
import com.adamkaz7.loyaltyprogram.exception.ConflictException;
import com.adamkaz7.loyaltyprogram.exception.NotFoundException;
import com.adamkaz7.loyaltyprogram.mapper.EarningRuleMapper;
import com.adamkaz7.loyaltyprogram.model.EarningRule;
import com.adamkaz7.loyaltyprogram.repository.EarningRuleRepository;
import com.adamkaz7.loyaltyprogram.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class EarningRuleService {
    private final EarningRuleRepository earningRuleRepository;
    private final EarningRuleMapper earningRuleMapper;
    private final ProgramRepository programRepository;

    @Transactional
    public EarningRuleDTO addRule(Long programId, EarningRuleRequestDTO dto) {
        if (!programRepository.existsById(programId)) {
            throw new NotFoundException("Program " + programId + " not found");
        }
        if (earningRuleRepository.existsByProgramIdAndEventType(programId, dto.eventType())) {
            throw new ConflictException(
                    "Earning rule for event " + dto.eventType() + " already exists in program " + programId
            );
        }
        validateDates(dto.startDate(), dto.endDate());
        EarningRule earningRule = earningRuleMapper.toEntity(dto, programId);
        earningRule = earningRuleRepository.save(earningRule);
        return earningRuleMapper.toDto(earningRule);
    }

    @Transactional(readOnly = true)
    public Page<EarningRuleDTO> getRulesByProgram(Long programId, Pageable pageable) {
        if (!programRepository.existsById(programId)) {
            throw new NotFoundException("Program " + programId + " not found");
        }
        return earningRuleRepository.findByProgramId(programId, pageable).map(earningRuleMapper::toDto);
    }

    @Transactional(readOnly = true)
    public EarningRuleDTO getRuleById(Long ruleId) {
        EarningRule earningRule = findRuleById(ruleId);
        return earningRuleMapper.toDto(earningRule);
    }

    @Transactional
    public EarningRuleDTO updateRule(Long ruleId, EarningRuleRequestDTO dto) {
        EarningRule earningRule = findRuleById(ruleId);
        if (earningRuleRepository.existsByProgramIdAndEventTypeAndIdNot(earningRule.getProgramId(), dto.eventType(), ruleId)) {
            throw new ConflictException(
                    "Earning rule for event " + dto.eventType() + " already exists in program " + earningRule.getProgramId()
            );
        }
        validateDates(dto.startDate(), dto.endDate());
        earningRule.updateRule(dto);
        earningRule = earningRuleRepository.save(earningRule);
        return earningRuleMapper.toDto(earningRule);
    }

    @Transactional
    public void deleteRule(Long ruleId) {
        EarningRule earningRule = findRuleById(ruleId);
        earningRuleRepository.delete(earningRule);
    }

    private EarningRule findRuleById(Long earningRuleId) {
        return earningRuleRepository.findById(earningRuleId)
                .orElseThrow(() -> new NotFoundException("Earning rule Id " + earningRuleId + " not found"));
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new BusinessException("End date cannot be before start date");
        }
    }
}
