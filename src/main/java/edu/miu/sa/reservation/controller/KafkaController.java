package edu.miu.sa.reservation.controller;

import edu.miu.sa.reservation.entity.Order;
import edu.miu.sa.reservation.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaService kafkaService;

    @Value("${kafka.topic}")
    private String topic;

    @PostMapping
    public void send(@RequestBody Order order) {

        kafkaService.publish(topic, order);
    }
}
