package com.bentorangel.smartinventory.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String sku,
        String name,
        BigDecimal price,
        Integer currentStock,
        boolean isStockCritical // Mandamos a regra de negócio mastigada pro front!
) {}