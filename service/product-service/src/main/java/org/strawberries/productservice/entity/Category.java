package org.strawberries.productservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> subCategories;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
