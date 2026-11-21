package com.adamkaz7.loyaltyprogram.service;

import com.adamkaz7.loyaltyprogram.dto.UserDTO;
import com.adamkaz7.loyaltyprogram.dto.UserRegistrationDTO;
import com.adamkaz7.loyaltyprogram.exception.ConflictException;
import com.adamkaz7.loyaltyprogram.exception.NotFoundException;
import com.adamkaz7.loyaltyprogram.mapper.UserMapper;
import com.adamkaz7.loyaltyprogram.model.User;
import com.adamkaz7.loyaltyprogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO addUser(UserRegistrationDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new ConflictException("User with email " + dto.email() + " already exists");
        }
        User user = userMapper.toEntity(dto, LocalDateTime.now());
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsers(String lastName, String email, Pageable pageable) {
        Page<User> users;
        if (email != null) {
            users = userRepository.findByEmail(email, pageable);
        } else if (lastName != null) {
            users = userRepository.findByLastNameContainingIgnoreCase(lastName, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        return users.map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long userId) {
        User user = findUserById(userId);
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserRegistrationDTO dto) {
        User user = findUserById(userId);
        if (userRepository.existsByEmailAndIdNot(dto.email(), userId)) {
            throw new ConflictException("User with email " + dto.email() + " already exists");
        }
        user.updateUser(dto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = findUserById(userId);
        userRepository.delete(user);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId + " not found"));
    }
}
