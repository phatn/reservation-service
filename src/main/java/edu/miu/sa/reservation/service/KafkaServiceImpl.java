package edu.miu.sa.reservation.service;

import edu.miu.sa.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, Reservation> kafkaTemplate;

    @Override
    public void publish(String topic, Reservation reservation) {
        kafkaTemplate.send(topic, reservation);
    }

    @Override
    @KafkaListener(id = "reserveId", topics = "${kafka.topic}")
    public void listen(Reservation reservation) {
        System.out.println("Received: " + reservation);
    }
}

