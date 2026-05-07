package com.cengiztansel.inventory_service.controller;
import com.cengiztansel.inventory_service.entity.Product;
import com.cengiztansel.inventory_service.repository.ProductRepository;
import com.cengiztansel.inventory_service.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
// @CrossOrigin(origins = "http://cengiztansel.duckdns.org:5173")
public class ProductController {

    private final ProductRepository productRepository;
    private final KafkaProducerService kafkaProducerService; // Servisimizi enjekte ettik

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        // 1. Ürünü Veritabanına Kaydet
        Product savedProduct = productRepository.save(product);

        // 2. Kafka'ya Event (Olay) Fırlat
        String eventMessage = String.format("Sisteme yeni ürün eklendi! ID: %d, Ürün: %s, Fiyat: %s",
                savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice());

        kafkaProducerService.sendMessage("product-events", eventMessage);

        // 3. Kaydedilen ürünü Frontend'e dön
        return savedProduct;
    }

    //En son ürünleri" getiren Redis metodu: 
    @GetMapping("/recent")
    @Cacheable(value = "recentProducts") // Bu metodun sonucu Redis'e kaydedilecek
    public List<Product> getRecentProducts() {
        log.info("Redis'te yok, veritabanından çekiliyor...");
        // Veritabanından son 5 ürünü ID'ye göre tersten çeken bir metod (Repository'de tanımlanmalı)
        return productRepository.findTop5ByOrderByIdDesc();
}



}
