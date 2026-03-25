package com.bentorangel.smartinventory.controllers;

import com.bentorangel.smartinventory.dtos.ProductRequestDTO;
import com.bentorangel.smartinventory.dtos.ProductResponseDTO;
import com.bentorangel.smartinventory.dtos.StockMovementResponseDTO;
import com.bentorangel.smartinventory.services.ProductService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Page<ProductResponseDTO>> listAll(
            @ParameterObject @PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        return ResponseEntity.ok(service.getAllProducts(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchByName(
            @RequestParam String name,
            @ParameterObject @PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        return ResponseEntity.ok(service.searchProductsByName(name, pageable));
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

    @GetMapping("/{id}/history")
    public ResponseEntity<List<StockMovementResponseDTO>> getHistory(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getProductHistory(id));
    }
}