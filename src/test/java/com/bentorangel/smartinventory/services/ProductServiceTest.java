package com.bentorangel.smartinventory.services;

import com.bentorangel.smartinventory.domain.Product;
import com.bentorangel.smartinventory.repositories.ProductRepository;
import com.bentorangel.smartinventory.repositories.StockMovementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Liga o Mockito no JUnit
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository; // "Finge" ser o banco de produtos

    @Mock
    private StockMovementRepository movementRepository; // "Finge" ser o banco de histórico

    @InjectMocks
    private ProductService productService; // A classe real que vamos testar, injetando os mocks nela

    @Test
    @DisplayName("Deve lançar exceção ao tentar deixar o estoque negativo")
    void shouldThrowExceptionWhenStockBecomesNegative() {
        // Arrange (Preparação)
        UUID productId = UUID.randomUUID();
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setCurrentStock(10); // O produto tem 10 no estoque

        // Quando o service procurar no banco fingido, devolvemos o nosso produto fingido
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act & Assert (Ação e Verificação)
        // Tentamos tirar 15 de um estoque de 10. Tem que estourar IllegalArgumentException!
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.updateStock(productId, -15, "Venda absurda");
        });

        // Verificamos se a mensagem de erro é exatamente a que programamos
        assertEquals("Operação inválida: O estoque não pode ficar negativo.", exception.getMessage());

        // Garantimos que o metodo .save() NUNCA foi chamado (ou seja, não salvou o erro no banco)
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Deve atualizar o estoque e salvar a movimentação com sucesso")
    void shouldUpdateStockSuccessfully() {
        // Arrange (Preparação)
        UUID productId = UUID.randomUUID();
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setCurrentStock(10); // Começa com 10
        mockProduct.setMinStockAlert(5);

        // Ensinando os mocks a se comportarem
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        // Act (Ação)
        // Adicionando 5 no estoque. O total deve ir para 15.
        var response = productService.updateStock(productId, 5, "Compra de Fornecedor");

        // Assert (Verificação)
        assertEquals(15, response.currentStock(), "O estoque final deve ser 15");

        // Verifica se o repository de produto chamou o metodo .save() exatamente 1 vez
        verify(productRepository, times(1)).save(any(Product.class));

        // Verifica se o log de auditoria foi salvo no banco de movimentações exatamente 1 vez!
        verify(movementRepository, times(1)).save(any());
    }
}