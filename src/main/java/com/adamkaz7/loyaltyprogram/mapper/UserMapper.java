package com.adamkaz7.loyaltyprogram.mapper;

import com.adamkaz7.loyaltyprogram.dto.UserDTO;
import com.adamkaz7.loyaltyprogram.dto.UserRegistrationDTO;
import com.adamkaz7.loyaltyprogram.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toEntity(UserRegistrationDTO dto, LocalDateTime registeredAt);
    UserDTO toDto(User user);
}

