package com.bentorangel.smartinventory.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record StockMovementResponseDTO(
        UUID id,
        Integer quantity,
        String type,
        String reason,
        LocalDateTime createdAt
) {}