package com.cengiztansel.inventory_service.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    // Kafka'daki 'product-events' kanalını sürekli dinleyen metod/Listener
    // groupId: Aynı mesajın birden fazla kez işlenmesini önlemek için kullanılır
    @KafkaListener(topics = "product-events", groupId = "inventory-group")
    public void consume(String message) {
        log.info(">>>>HEYYYYY....BURAYA BAKKKKKK -->  KAFKA'DAN MESAJ ALINDI: {}", message);
        
        // Burada gerçek hayatta şu işlemler yapılır:
        // - E-posta/SMS gönderimi
        // - Başka bir veritabanını güncelleme
        // - Bir log dosyasına yazma
    }
}
