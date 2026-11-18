package com.adamkaz7.loyaltyprogram.mapper;

import com.adamkaz7.loyaltyprogram.dto.ProgramDTO;
import com.adamkaz7.loyaltyprogram.model.LoyaltyProgram;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProgramMapper {
    @Mapping(target = "id", ignore = true)
    LoyaltyProgram toEntity(ProgramDTO dto);
    ProgramDTO toDto(LoyaltyProgram program);
}
