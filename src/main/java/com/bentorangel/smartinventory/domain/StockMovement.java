package com.bentorangel.smartinventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_movements")
@Getter
@Setter
@NoArgsConstructor
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity; // Ex: -5 ou +10

    @Column(nullable = false)
    private String type; // "ENTRY" ou "EXIT"

    private String reason; // Ex: "Venda PDV", "Reposição Fornecedor"

    @CreationTimestamp
    private LocalDateTime createdAt;
}