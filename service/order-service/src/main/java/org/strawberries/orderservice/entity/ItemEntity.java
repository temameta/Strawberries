package org.strawberries.orderservice.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column(nullable = false, updatable = false)
    UUID productId;
    @Column(nullable = false, updatable = false)
    int productAmount;
}
