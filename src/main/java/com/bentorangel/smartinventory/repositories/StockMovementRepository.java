package com.bentorangel.smartinventory.repositories;

import com.bentorangel.smartinventory.domain.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> {
    // Buscar histórico de um produto específico
    List<StockMovement> findByProductIdOrderByCreatedAtDesc(UUID productId);
}