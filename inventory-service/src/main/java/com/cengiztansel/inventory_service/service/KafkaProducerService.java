package com.cengiztansel.inventory_service.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j // Konsola log yazmak için kullanıyoruz
public class KafkaProducerService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(KafkaProducerService.class);
    // Spring Kafka'nın bize sunduğu ana mesajlaşma aracı
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        log.info("Kafka'ya mesaj gönderiliyor. Topic: {}, Mesaj: {}", topic, message);
        kafkaTemplate.send(topic, message);
    }


}
