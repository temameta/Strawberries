package org.strawberries.orderservice.entity;

import jakarta.persistence.*;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.strawberries.orderapi.codegen.types.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column(nullable = false, updatable = false)
    UUID userId;
    @Column(nullable = false, updatable = false)
    String address;
    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false, updatable = false)
    List<ItemEntity> items;
    @Column(nullable = false, updatable = false)
    OffsetDateTime timeStamp;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    OrderStatus status;
    @Column(nullable = false, updatable = false)
    BigDecimal totalPrice;
    @CreatedDate
    OffsetDateTime createdAt;
    @LastModifiedDate
    OffsetDateTime updatedAt;
}
