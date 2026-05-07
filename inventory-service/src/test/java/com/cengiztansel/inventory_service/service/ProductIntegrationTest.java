package com.cengiztansel.inventory_service.service;

import com.cengiztansel.inventory_service.entity.Product;
import com.cengiztansel.inventory_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ProductIntegrationTest {

    // Test için geçici bir Postgres imajı tanımlıyoruz
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // Uygulamanın Docker'daki bu geçici DB'ye bağlanmasını sağlıyoruz
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndRetrieveProduct() {
        Product product = new Product();
        product.setName("Integration Test Item");
        product.setPrice(150.0);

        productRepository.save(product);

        assertEquals(1, productRepository.findAll().size());
        assertEquals("Integration Test Item", productRepository.findAll().get(0).getName());
    }
}
