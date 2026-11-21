package com.adamkaz7.loyaltyprogram.mapper;

import com.adamkaz7.loyaltyprogram.dto.RewardDTO;
import com.adamkaz7.loyaltyprogram.dto.RewardRequestDTO;
import com.adamkaz7.loyaltyprogram.model.Reward;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RewardMapper {
    @Mapping(target = "id", ignore = true)
    Reward toEntity(RewardRequestDTO dto, Long programId);
    RewardDTO toDto(Reward reward, boolean available);
}
