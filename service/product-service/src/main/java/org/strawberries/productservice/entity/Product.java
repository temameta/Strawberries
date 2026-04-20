package org.strawberries.productservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private boolean active;
    @Column
    private String imageUrl;
    @ManyToOne
    private Category category;
}
