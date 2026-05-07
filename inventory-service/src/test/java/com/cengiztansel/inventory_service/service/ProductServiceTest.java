package com.cengiztansel.inventory_service.service;

import com.cengiztansel.inventory_service.entity.Product;
import com.cengiztansel.inventory_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Mockito desteğini aktif eder
class ProductServiceTest {

    @Mock // Veritabanı bağımlılığını taklit eder (Gerçek DB'ye gitmez)
    private ProductRepository productRepository;

    @Mock // Kafka bağımlılığını taklit eder (Gerçek Kafka'ya mesaj atmaz)
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks // Yukarıdaki mock'ları ProductService içine otomatik yerleştirir
    private ProductService productService;

    @Test
    void createProduct_ShouldSaveToDbAndSendKafkaMessage() {
        // --- 1. HAZIRLIK (GIVEN) ---
        Product inputProduct = new Product();
        inputProduct.setName("Asus Laptop");
        inputProduct.setPrice(35000.0);

        Product savedProduct = new Product();
        savedProduct.setId(100L);
        savedProduct.setName("Asus Laptop");
        savedProduct.setPrice(35000.0);

        // Repository save edildiğinde bize ID'li halini dönsün diyoruz // Repository çağrıldığında sahte ürünü dön
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // --- 2. ÇALIŞTIRMA (WHEN) ---
        Product result = productService.createProduct(inputProduct);

        // --- 3. DOĞRULAMA (THEN) ---
        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Asus Laptop", result.getName());

        // Veritabanına kayıt işlemi GERÇEKTEN çağrıldı mı?
// Repository save metodu tam olarak 1 kere çağrıldı mı?
        verify(productRepository, times(1)).save(any(Product.class));

        // Kafka'ya "product-events" topic'ine mesaj GERÇEKTEN gönderildi mi?
        verify(kafkaTemplate, times(1)).send(eq("product-events"), anyString());

        System.out.println("Unit Test Başarıyla Geçti: Ürün kaydedildi ve Kafka mesajı tetiklendi!");
    }
}
