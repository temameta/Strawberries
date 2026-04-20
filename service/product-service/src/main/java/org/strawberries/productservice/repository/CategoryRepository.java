package org.strawberries.productservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.strawberries.productservice.entity.CategoryEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    Page<CategoryEntity> findAllByActive(Pageable pageable, Boolean active);

    Optional<CategoryEntity> findByIdAndActiveTrue(UUID id);

    Optional<CategoryEntity> findByIdAndActiveFalse(UUID id);
}
