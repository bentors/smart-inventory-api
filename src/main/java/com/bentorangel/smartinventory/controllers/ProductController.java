package com.bentorangel.smartinventory.controllers;

import com.bentorangel.smartinventory.dtos.ProductRequestDTO;
import com.bentorangel.smartinventory.dtos.ProductResponseDTO;
import com.bentorangel.smartinventory.dtos.StockMovementResponseDTO;
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

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listAll() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductResponseDTO> updateStock(
            @PathVariable UUID id,
            @RequestParam Integer quantity,
            @RequestParam(required = false) String reason) {

        return ResponseEntity.ok(service.updateStock(id, quantity, reason));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(service.searchProductsByName(name));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<StockMovementResponseDTO>> getHistory(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getProductHistory(id));
    }
}