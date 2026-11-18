package com.adamkaz7.loyaltyprogram.service;

import com.adamkaz7.loyaltyprogram.dto.ProgramDTO;
import com.adamkaz7.loyaltyprogram.exception.BusinessException;
import com.adamkaz7.loyaltyprogram.exception.ConflictException;
import com.adamkaz7.loyaltyprogram.exception.NotFoundException;
import com.adamkaz7.loyaltyprogram.mapper.ProgramMapper;
import com.adamkaz7.loyaltyprogram.model.LoyaltyProgram;
import com.adamkaz7.loyaltyprogram.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;

    @Transactional
    public ProgramDTO addProgram(ProgramDTO dto) {
        if (programRepository.existsByName(dto.name())) {
            throw new ConflictException("Program with name " + dto.name() + " already exists");
        }
        validateDates(dto.startDate(), dto.endDate());
        LoyaltyProgram loyaltyProgram = programMapper.toEntity(dto);
        loyaltyProgram = programRepository.save(loyaltyProgram);
        return programMapper.toDto(loyaltyProgram);
    }

    @Transactional(readOnly = true)
    public Page<ProgramDTO> getAllPrograms(boolean active, Pageable pageable) {
        Page<LoyaltyProgram> programs = active
                ? programRepository.findActiveOn(LocalDate.now(), pageable)
                : programRepository.findAll(pageable);
        return programs.map(programMapper::toDto);
    }

    @Transactional(readOnly = true)
    public ProgramDTO getProgramById(Long programId) {
        LoyaltyProgram loyaltyProgram = findProgramById(programId);
        return programMapper.toDto(loyaltyProgram);
    }

    @Transactional
    public ProgramDTO updateProgram(Long programId, ProgramDTO dto) {
        LoyaltyProgram loyaltyProgram = findProgramById(programId);
        if (programRepository.existsByNameAndIdNot(dto.name(), programId)) {
            throw new ConflictException("Program with name " + dto.name() + " already exists");
        }
        validateDates(dto.startDate(), dto.endDate());
        loyaltyProgram.updateProgram(dto);
        loyaltyProgram = programRepository.save(loyaltyProgram);
        return programMapper.toDto(loyaltyProgram);
    }

    @Transactional
    public void deleteProgram(Long programId) {
        LoyaltyProgram program = findProgramById(programId);
        programRepository.delete(program);
    }

    private LoyaltyProgram findProgramById(Long programId) {
         return programRepository.findById(programId)
                 .orElseThrow(() -> new NotFoundException("Program " + programId + " not found"));
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new BusinessException("End date cannot be before start date");
        }
    }
}
