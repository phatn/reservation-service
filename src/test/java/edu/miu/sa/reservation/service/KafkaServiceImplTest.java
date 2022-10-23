package edu.miu.sa.reservation.service;

import edu.miu.sa.reservation.entity.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

@Testcontainers
@SpringBootTest
@ActiveProfiles("dev")
class KafkaServiceImplTest {

    @Autowired
    private KafkaTemplate<String, Reservation> kafkaTemplate;

    @Autowired
    private KafkaService kafkaService;

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.0.1"));

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Test
    public void should_publish() throws InterruptedException {
        UUID reservationId = UUID.randomUUID();
        UUID propertyId = UUID.randomUUID();
        Reservation reservation = new Reservation(reservationId, "phatnguyen@outlook.com",propertyId, "Credit", 200, 3, 600);
        kafkaService.publish("RESERVATION_COMPLETED", reservation);
    }
}