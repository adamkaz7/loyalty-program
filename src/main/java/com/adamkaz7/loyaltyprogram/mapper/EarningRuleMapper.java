package com.adamkaz7.loyaltyprogram.mapper;

import com.adamkaz7.loyaltyprogram.dto.EarningRuleDTO;
import com.adamkaz7.loyaltyprogram.dto.EarningRuleRequestDTO;
import com.adamkaz7.loyaltyprogram.model.EarningRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EarningRuleMapper {
    @Mapping(target = "id", ignore = true)
    EarningRule toEntity(EarningRuleRequestDTO dto, Long programId);
    EarningRuleDTO toDto(EarningRule rule);
}
