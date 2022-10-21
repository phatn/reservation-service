package edu.miu.sa.reservation.service;

import edu.miu.sa.reservation.entity.Order;

public interface KafkaService {

    void publish(String topic, Order message);

    void listen(Order message);
}
