package com.bentorangel.smartinventory.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank(message = "O SKU não pode estar em branco")
        String sku,

        @NotBlank(message = "O nome do produto é obrigatório")
        String name,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal price,

        @NotNull(message = "O custo é obrigatório")
        @PositiveOrZero(message = "O custo não pode ser negativo")
        BigDecimal cost,

        @NotNull(message = "O estoque atual é obrigatório")
        @PositiveOrZero(message = "O estoque não pode ser negativo")
        Integer currentStock,

        @NotNull(message = "O alerta de estoque mínimo é obrigatório")
        @PositiveOrZero(message = "O alerta de estoque não pode ser negativo")
        Integer minStockAlert
) {}