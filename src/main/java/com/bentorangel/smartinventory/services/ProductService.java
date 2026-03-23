package com.bentorangel.smartinventory.services;

import com.bentorangel.smartinventory.domain.Product;
import com.bentorangel.smartinventory.dtos.ProductRequestDTO;
import com.bentorangel.smartinventory.dtos.ProductResponseDTO;
import com.bentorangel.smartinventory.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // Método para criar produto
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {

        // 1. Converte o DTO para a Entidade do Banco
        Product product = new Product();
        product.setSku(requestDTO.sku());
        product.setName(requestDTO.name());
        product.setPrice(requestDTO.price());
        product.setCost(requestDTO.cost());
        product.setCurrentStock(requestDTO.currentStock());
        product.setMinStockAlert(requestDTO.minStockAlert());

        // 2. Salva no banco de dados
        Product savedProduct = repository.save(product);

        // 3. Converte a Entidade salva de volta para um DTO de resposta
        return new ProductResponseDTO(
                savedProduct.getId(),
                savedProduct.getSku(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getCurrentStock(),
                savedProduct.isStockCritical() // Usa o metodo da nossa entidade!
        );
    }

    // Metodo para listar todos
    public List<ProductResponseDTO> getAllProducts() {
        return repository.findAll()
                .stream()
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getSku(),
                        product.getName(),
                        product.getPrice(),
                        product.getCurrentStock(),
                        product.isStockCritical()
                ))
                .collect(Collectors.toList());
    }

    // Buscar um produto específico pelo ID
    public ProductResponseDTO getProductById(UUID id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado para o ID: " + id));

        return new ProductResponseDTO(
                product.getId(), product.getSku(), product.getName(),
                product.getPrice(), product.getCurrentStock(), product.isStockCritical()
        );
    }

    // Atualizar apenas o stock do produto (Entrada ou Saída)
    public ProductResponseDTO updateStock(UUID id, Integer quantityToAddOrRemove) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado para o ID: " + id));

        int newStock = product.getCurrentStock() + quantityToAddOrRemove;

        if (newStock < 0) {
            throw new IllegalArgumentException("Operação inválida: O stock não pode ficar negativo.");
        }

        product.setCurrentStock(newStock);
        Product updatedProduct = repository.save(product);

        return new ProductResponseDTO(
                updatedProduct.getId(), updatedProduct.getSku(), updatedProduct.getName(),
                updatedProduct.getPrice(), updatedProduct.getCurrentStock(), updatedProduct.isStockCritical()
        );
    }

    // Eliminar um produto
    public void deleteProduct(UUID id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado para o ID: " + id));

        repository.delete(product);
    }

    public List<ProductResponseDTO> searchProductsByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(product -> new ProductResponseDTO(
                        product.getId(), product.getSku(), product.getName(),
                        product.getPrice(), product.getCurrentStock(), product.isStockCritical()
                ))
                .collect(Collectors.toList());
    }
}