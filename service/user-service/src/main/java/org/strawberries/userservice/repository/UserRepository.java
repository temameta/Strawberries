package org.strawberries.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.strawberries.userservice.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findAllByIsActive(Pageable pageable, boolean isActive);
    Optional<User> findByIdAndIsActiveTrue(UUID id);
    boolean existsByEmailAndIsActiveTrue(String email);
    boolean existsByPhoneAndIsActiveTrue(String phone);
}
