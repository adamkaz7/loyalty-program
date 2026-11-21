package com.adamkaz7.loyaltyprogram.repository;

import com.adamkaz7.loyaltyprogram.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
    Page<User> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
    Page<User> findByEmail(String email, Pageable pageable);
}
