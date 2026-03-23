package com.bentorangel.smartinventory.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldReturnTrueWhenStockIsBelowOrEqualMinimum() {
        // Preparação (Arrange)
        Product product = new Product();
        product.setCurrentStock(5);
        product.setMinStockAlert(10);

        // Ação e Verificação (Act & Assert)
        assertTrue(product.isStockCritical(), "O estoque deveria ser considerado crítico (5 <= 10)");
    }

    @Test
    void shouldReturnFalseWhenStockIsAboveMinimum() {
        // Preparação (Arrange)
        Product product = new Product();
        product.setCurrentStock(15);
        product.setMinStockAlert(10);

        // Ação e Verificação (Act & Assert)
        assertFalse(product.isStockCritical(), "O estoque NÃO deveria ser considerado crítico (15 > 10)");
    }
}