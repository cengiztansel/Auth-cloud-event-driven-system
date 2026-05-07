package com.cengiztansel.inventory_service.service;
import com.cengiztansel.inventory_service.entity.Product;
import com.cengiztansel.inventory_service.repository.ProductRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProductService(ProductRepository productRepository,
                          KafkaTemplate<String, String> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Product saveProduct(Product product) {
        Product saved = productRepository.save(product);
       // kafkaTemplate.send("product-topic", "Product saved: " + saved.getName());
        kafkaTemplate.send("product-events", "Product saved: " + saved.getName());
        return saved;
    }

    public Product createProduct(Product mockProduct) {
        Product product = productRepository.save(mockProduct);
        //kafkaTemplate.send("product-topic", "Product saved: " + saved.getName());
        kafkaTemplate.send("product-events", "Product saved: " + product.getName());
        return product;
    }
}