package org.strawberries.productservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter @Setter
public abstract class AuditableEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected OffsetDateTime createdAt;
    @LastModifiedDate
    @Column
    protected OffsetDateTime updatedAt;
}
