package com.bentorangel.smartinventory.repositories;

import com.bentorangel.smartinventory.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    // Busca exata pelo SKU
    Optional<Product> findBySku(String sku);

    // Busca por parte do nome (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);
}