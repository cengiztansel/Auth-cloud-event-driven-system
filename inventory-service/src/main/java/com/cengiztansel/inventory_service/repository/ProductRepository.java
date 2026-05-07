package com.cengiztansel.inventory_service.repository;

import com.cengiztansel.inventory_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop5ByOrderByIdDesc();
    // findAll, save, delete gibi metodlar otomatik gelir.
}
