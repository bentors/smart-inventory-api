package com.bentorangel.smartinventory.services;

import com.bentorangel.smartinventory.domain.Product;
import com.bentorangel.smartinventory.domain.StockMovement;
import com.bentorangel.smartinventory.dtos.ProductRequestDTO;
import com.bentorangel.smartinventory.dtos.ProductResponseDTO;
import com.bentorangel.smartinventory.dtos.StockMovementResponseDTO;
import com.bentorangel.smartinventory.repositories.ProductRepository;
import com.bentorangel.smartinventory.repositories.StockMovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final StockMovementRepository movementRepository;

    // Construtor com as duas dependências
    public ProductService(ProductRepository repository, StockMovementRepository movementRepository) {
        this.repository = repository;
        this.movementRepository = movementRepository;
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Product product = new Product();
        product.setSku(requestDTO.sku());
        product.setName(requestDTO.name());
        product.setPrice(requestDTO.price());
        product.setCost(requestDTO.cost());
        product.setCurrentStock(requestDTO.currentStock());
        product.setMinStockAlert(requestDTO.minStockAlert());

        Product savedProduct = repository.save(product);

        // Opcional: Registrar a movimentação inicial de estoque
        saveMovement(savedProduct, savedProduct.getCurrentStock(), "Criação inicial do produto");

        return mapToResponseDTO(savedProduct);
    }

    // Listar todos com Paginação
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::mapToResponseDTO); // O Page já tem o map() nativo!
    }

    public ProductResponseDTO getProductById(UUID id) {
        Product product = findProductOrThrow(id);
        return mapToResponseDTO(product);
    }

    @Transactional
    public ProductResponseDTO updateStock(UUID id, Integer quantity, String reason) {
        Product product = findProductOrThrow(id);

        int newStock = product.getCurrentStock() + quantity;
        if (newStock < 0) {
            throw new IllegalArgumentException("Operação inválida: O estoque não pode ficar negativo.");
        }

        product.setCurrentStock(newStock);
        Product updatedProduct = repository.save(product);

        // REGISTRO DE AUDITORIA
        saveMovement(updatedProduct, quantity, reason);

        return mapToResponseDTO(updatedProduct);
    }

    @Transactional
    public void deleteProduct(UUID id) {
        Product product = findProductOrThrow(id);
        repository.delete(product);
    }

    public Page<ProductResponseDTO> searchProductsByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable)
                .map(this::mapToResponseDTO);
    }

    // --- MÉTODOS AUXILIARES (PRIVADOS) ---

    // Centraliza a lógica de "Ou acha ou explode erro"
    private Product findProductOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado para o ID: " + id));
    }

    // Centraliza a conversão de Entidade para DTO (DRY - Don't Repeat Yourself)
    private ProductResponseDTO mapToResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getPrice(),
                product.getCurrentStock(),
                product.isStockCritical()
        );
    }

    // Centraliza a criação do log de movimentação
    private void saveMovement(Product product, Integer quantity, String reason) {
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setQuantity(quantity);
        movement.setType(quantity > 0 ? "ENTRY" : "EXIT");
        movement.setReason(reason != null ? reason : "Ajuste manual");
        movementRepository.save(movement);
    }

    public List<StockMovementResponseDTO> getProductHistory(UUID productId) {
        // Primeiro verificamos se o produto existe
        findProductOrThrow(productId);

        return movementRepository.findByProductIdOrderByCreatedAtDesc(productId)
                .stream()
                .map(movement -> new StockMovementResponseDTO(
                        movement.getId(),
                        movement.getQuantity(),
                        movement.getType(),
                        movement.getReason(),
                        movement.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}