package edu.miu.sa.reservation.service;

import edu.miu.sa.reservation.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @Override
    public void publish(String topic, Order message) {
        kafkaTemplate.send(topic, message);
    }

    @Override
    @KafkaListener(id = "reserveId", topics = "${kafka.topic}")
    public void listen(Order message) {
        System.out.println("Received: " + message);
    }
}

