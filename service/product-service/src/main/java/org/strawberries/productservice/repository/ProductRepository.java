package org.strawberries.productservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.strawberries.productservice.entity.ProductEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    Page<ProductEntity> findAllByActive(Pageable pageable, Boolean active);

    Optional<ProductEntity> findByIdAndActiveTrue(UUID id);
}
