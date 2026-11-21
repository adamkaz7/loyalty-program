package com.adamkaz7.loyaltyprogram.service;

import com.adamkaz7.loyaltyprogram.dto.RewardDTO;
import com.adamkaz7.loyaltyprogram.dto.RewardRequestDTO;
import com.adamkaz7.loyaltyprogram.exception.BusinessException;
import com.adamkaz7.loyaltyprogram.exception.NotFoundException;
import com.adamkaz7.loyaltyprogram.mapper.RewardMapper;
import com.adamkaz7.loyaltyprogram.model.Reward;
import com.adamkaz7.loyaltyprogram.repository.ProgramRepository;
import com.adamkaz7.loyaltyprogram.repository.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RewardService {
    private final RewardRepository rewardRepository;
    private final RewardMapper rewardMapper;
    private final ProgramRepository programRepository;

    @Transactional
    public RewardDTO addReward(Long programId, RewardRequestDTO dto) {
        if (!programRepository.existsById(programId)) {
            throw new NotFoundException("Program " + programId + " not found");
        }
        validateDates(dto.startDate(), dto.endDate());
        Reward reward = rewardMapper.toEntity(dto, programId);
        reward = rewardRepository.save(reward);
        return toDto(reward);
    }

    @Transactional(readOnly = true)
    public Page<RewardDTO> getRewardsByProgram(Long programId, Pageable pageable) {
        if (!programRepository.existsById(programId)) {
            throw new NotFoundException("Program " + programId + " not found");
        }
        return rewardRepository.findByProgramId(programId, pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public RewardDTO getRewardById(Long rewardId) {
        Reward reward = findRewardById(rewardId);
        return toDto(reward);
    }

    @Transactional
    public RewardDTO updateReward(Long rewardId, RewardRequestDTO dto) {
        Reward reward = findRewardById(rewardId);
        validateDates(dto.startDate(), dto.endDate());
        reward.updateReward(dto);
        reward = rewardRepository.save(reward);
        return toDto(reward);
    }

    @Transactional
    public void deleteReward(Long rewardId) {
        Reward reward = findRewardById(rewardId);
        rewardRepository.delete(reward);
    }

    private RewardDTO toDto(Reward reward) {
        return rewardMapper.toDto(reward, reward.isAvailableOn(LocalDate.now()));
    }

    private Reward findRewardById(Long rewardId) {
        return rewardRepository.findById(rewardId)
                .orElseThrow(() -> new NotFoundException("Reward " + rewardId + " not found"));
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new BusinessException("End date cannot be before start date");
        }
    }
}
