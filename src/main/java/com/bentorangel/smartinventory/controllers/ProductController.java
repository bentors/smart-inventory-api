package com.bentorangel.smartinventory.controllers;

import com.bentorangel.smartinventory.dtos.ProductRequestDTO;
import com.bentorangel.smartinventory.dtos.ProductResponseDTO;
import com.bentorangel.smartinventory.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO response = service.createProduct(requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content (padrão correto para deleção)
    }

    // Usamos o PATCH porque estamos a modificar apenas uma parte do recurso (o stock)
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductResponseDTO> updateStock(
            @PathVariable UUID id,
            @RequestParam Integer quantity) { // quantity pode ser positivo (entrada) ou negativo (saída)

        return ResponseEntity.ok(service.updateStock(id, quantity));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(service.searchProductsByName(name));
    }
}